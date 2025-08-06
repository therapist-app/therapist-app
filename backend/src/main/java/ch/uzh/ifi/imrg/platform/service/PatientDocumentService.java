package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.DocumentOverviewDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.repository.*;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDocumentFromTherapistDocumentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientDocumentMapper;
import ch.uzh.ifi.imrg.platform.utils.*;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.xml.sax.ContentHandler;

@Service
@Transactional
public class PatientDocumentService {

  private static final Logger logger = LoggerFactory.getLogger(PatientDocumentService.class);

  private final PatientRepository patientRepository;
  private final TherapistRepository therapistRepository;
  private final TherapistDocumentRepository therapistDocumentRepository;
  private final PatientDocumentRepository patientDocumentRepository;

  public PatientDocumentService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository,
      @Qualifier("therapistDocumentRepository")
          TherapistDocumentRepository therapistDocumentRepository,
      @Qualifier("patientDocumentRepository") PatientDocumentRepository patientDocumentRepository) {
    this.patientRepository = patientRepository;
    this.therapistRepository = therapistRepository;
    this.therapistDocumentRepository = therapistDocumentRepository;
    this.patientDocumentRepository = patientDocumentRepository;
  }

  public void uploadPatientDocument(
      String patientId, MultipartFile file, Boolean isSharedWithPatient, String therapistId)
      throws IOException {

    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
    SecurityUtil.checkOwnership(patient, therapistId);

    String rawText = readFullText(file);

    boolean needsSummary = rawText.length() > DocumentSummarizerUtil.MAX_SUMMARY_CHARS;
    String extractedText =
        needsSummary ? rawText.substring(0, DocumentSummarizerUtil.MAX_SUMMARY_CHARS) : rawText;

    PatientDocument patientDocument = new PatientDocument();
    patientDocument.setIsSharedWithPatient(isSharedWithPatient);
    patientDocument.setPatient(patient);
    patientDocument.setFileName(file.getOriginalFilename());
    patientDocument.setFileType(file.getContentType());
    patientDocument.setExtractedText(extractedText);
    try {
      patientDocument.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to read file bytes", e);
    }

    if (isSharedWithPatient) {
      DocumentOverviewDTOPatientAPI dto =
          FileUploadUtil.uploadFile("/coach/patients/" + patientId + "/documents", file);
      patientDocument.setExternalId(dto.getId());
    }

    patientDocument = patientDocumentRepository.save(patientDocument);
    final String docId = patientDocument.getId();

    if (needsSummary) {
      final String raw = rawText;

      CompletableFuture.runAsync(
          () -> {
            try {
              Therapist freshTherapist = therapistRepository.findById(therapistId).orElse(null);
              if (freshTherapist == null) {
                logger.warn("Therapist {} vanished before summarisation", therapistId);
                return;
              }

              String summary =
                  DocumentSummarizerUtil.summarize(raw, freshTherapist, therapistRepository);

              PatientDocument upd = patientDocumentRepository.findById(docId).orElse(null);
              if (upd != null) {
                upd.setExtractedText(summary);
                patientDocumentRepository.save(upd);
              }
            } catch (Exception ex) {
              logger.error(
                  "Asynchronous LLM summarisation failed for PatientDocument {}", docId, ex);
            }
          });
    }
  }

  public void createPatientDocumentFromTherapistDocument(
      CreatePatientDocumentFromTherapistDocumentDTO dto, String therapistId) {

    TherapistDocument therapistDocument =
        therapistDocumentRepository
            .findById(dto.getTherapistDocumentId())
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Therapist document not found"));
    SecurityUtil.checkOwnership(therapistDocument, therapistId);

    PatientDocument patientDocument = new PatientDocument();
    patientDocument.setPatient(
        patientRepository
            .findById(dto.getPatientId())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found")));
    patientDocument.setFileName(therapistDocument.getFileName());
    patientDocument.setFileType(therapistDocument.getFileType());
    patientDocument.setFileData(therapistDocument.getFileData());
    patientDocument.setExtractedText(therapistDocument.getExtractedText());

    patientDocumentRepository.save(patientDocument);
  }

  public List<PatientDocumentOutputDTO> getDocumentsOfPatient(
      String patientId, String therapistId) {

    Patient patient = patientRepository.getPatientById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    return patient.getPatientDocuments().stream()
        .map(PatientDocumentMapper.INSTANCE::convertEntityToPatientDocumentOutputDTO)
        .collect(Collectors.toList());
  }

  public PatientDocument downloadPatientDocument(String patientDocumentId, String therapistId) {

    PatientDocument patientDocument =
        patientDocumentRepository
            .findById(patientDocumentId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient document not found"));
    SecurityUtil.checkOwnership(patientDocument, therapistId);

    return patientDocument;
  }

  public void deleteFile(String patientDocumentId, String therapistId) {

    PatientDocument patientDocument = patientDocumentRepository.getReferenceById(patientDocumentId);
    SecurityUtil.checkOwnership(patientDocument, therapistId);

    if (patientDocument.getIsSharedWithPatient() && patientDocument.getExternalId() != null) {
      try {
        PatientAppAPIs.coachDocumentControllerPatientAPI
            .deleteDocument(patientDocument.getPatient().getId(), patientDocument.getExternalId())
            .block();
      } catch (WebClientResponseException.NotFound e) {
        logger.info(e.getMessage());
      }
    }

    patientDocument.getPatient().getPatientDocuments().remove(patientDocument);
  }

  private String readFullText(MultipartFile file) {
    try (InputStream stream = file.getInputStream()) {
      AutoDetectParser parser = new AutoDetectParser();
      ContentHandler handler = new BodyContentHandler(-1);
      Metadata metadata = new Metadata();
      ParseContext context = new ParseContext();
      parser.parse(stream, handler, metadata, context);
      return handler.toString();
    } catch (Exception e) {
      return DocumentParserUtil.extractText(file);
    }
  }
}
