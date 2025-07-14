package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.repository.PatientDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistDocumentRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDocumentFromTherapistDocumentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientDocumentMapper;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
public class PatientDocumentService {

  private final PatientRepository patientRepository;
  private final TherapistDocumentRepository therapistDocumentRepository;
  private final PatientDocumentRepository patientDocumentRepository;

  public PatientDocumentService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapistDocumentRepository")
          TherapistDocumentRepository therapistDocumentRepository,
      @Qualifier("patientDocumentRepository") PatientDocumentRepository patientDocumentRepository) {
    this.patientRepository = patientRepository;
    this.therapistDocumentRepository = therapistDocumentRepository;
    this.patientDocumentRepository = patientDocumentRepository;
  }

  public void uploadPatientDocument(
      String patientId, MultipartFile file, Boolean isSharedWithPatient, String therapistId)
      throws IOException {

    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
    SecurityUtil.checkOwnership(patient, therapistId);

    String extractedText = DocumentParserUtil.extractText(file);
    PatientDocument patientDocument = new PatientDocument();
    patientDocument.setIsSharedWithPatient(isSharedWithPatient);
    patientDocument.setPatient(patient);
    patientDocument.setFileName(file.getOriginalFilename());
    patientDocument.setFileType(file.getContentType());
    try {
      patientDocument.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file bytes", e);
    }
    patientDocument.setExtractedText(extractedText);

    if (isSharedWithPatient) {
      File convertedFile = null;
      try {
        String boundary =
            "--------------------------" + UUID.randomUUID().toString().replace("-", "");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String lineEnd = "\r\n";

        // --- Manually construct the multipart body ---
        bos.write(("--" + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
        bos.write(
            ("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + file.getOriginalFilename()
                    + "\""
                    + lineEnd)
                .getBytes(StandardCharsets.UTF_8));
        bos.write(
            ("Content-Type: " + file.getContentType() + lineEnd).getBytes(StandardCharsets.UTF_8));
        bos.write(lineEnd.getBytes(StandardCharsets.UTF_8));
        bos.write(file.getBytes());
        bos.write(lineEnd.getBytes(StandardCharsets.UTF_8));
        bos.write(("--" + boundary + "--" + lineEnd).getBytes(StandardCharsets.UTF_8));

        byte[] multipartBody = bos.toByteArray();

        WebClient webClient =
            WebClient.builder()
                .baseUrl(
                    "https://backend-patient-app-main.jonas-blum.ch") // Remember to use the correct
                // URL
                .build();

        // 🚀 THE FIX IS HERE 🚀
        // We use .header() to send the Content-Type string "as-is", with the space.
        String contentTypeHeader = "multipart/form-data; boundary=" + boundary;

        webClient
            .post()
            .uri("/coach/patients/{patientId}/documents", patientId)
            .header(
                HttpHeaders.CONTENT_TYPE,
                contentTypeHeader) // Use .header() instead of .contentType()
            .header(
                "X-Coach-Key",
                "ThisIsTheDevelopmentCoachAccessKeyItDoesNotHaveToBeSuperSecretButLongEnough")
            .contentLength(multipartBody.length)
            .bodyValue(multipartBody)
            .retrieve()
            .toBodilessEntity()
            .block();
      } catch (Exception e) {
        throw e;
      } finally {
        if (convertedFile != null) {
          convertedFile.delete();
        }
      }
    }

    patientDocumentRepository.save(patientDocument);
  }

  public void createPatientDocumentFromTherapistDocument(
      CreatePatientDocumentFromTherapistDocumentDTO createPatientDocumentFromTherapistDocumentDTO,
      String therapistId) {
    TherapistDocument therapistDocument =
        therapistDocumentRepository
            .findById(createPatientDocumentFromTherapistDocumentDTO.getTherapistDocumentId())
            .orElseThrow(() -> new RuntimeException("Therapist document not found"));

    SecurityUtil.checkOwnership(therapistDocument, therapistId);

    PatientDocument patientDocument = new PatientDocument();
    patientDocument.setPatient(
        patientRepository
            .findById(createPatientDocumentFromTherapistDocumentDTO.getPatientId())
            .orElseThrow(() -> new RuntimeException("Patient not found")));
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
            .orElseThrow(() -> new RuntimeException("Patient document not found"));
    SecurityUtil.checkOwnership(patientDocument, therapistId);

    return patientDocument;
  }

  public void deleteFile(String patientDocumentId, String therapistId) {

    PatientDocument patientDocument = patientDocumentRepository.getReferenceById(patientDocumentId);
    SecurityUtil.checkOwnership(patientDocument, therapistId);
    patientDocument.getPatient().getPatientDocuments().remove(patientDocument);
  }
}
