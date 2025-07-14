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
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
public class PatientDocumentService {

  private final PatientRepository patientRepository;
  private final TherapistDocumentRepository therapistDocumentRepository;
  private final PatientDocumentRepository patientDocumentRepository;
  private static final Logger log = LoggerFactory.getLogger(PatientDocumentService.class);

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
      try {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 2. Create an HttpEntity for the file part.
        // This lets Spring know about the file's name and content type.
        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentDispositionFormData("file", file.getOriginalFilename());
        fileHeaders.setContentType(MediaType.parseMediaType(file.getContentType()));
        HttpEntity<Resource> fileEntity = new HttpEntity<>(file.getResource(), fileHeaders);

        // 3. Add the file entity to the body map.
        body.add("file", fileEntity);

        WebClient webClient =
            WebClient.builder().baseUrl("https://backend-patient-app-main.jonas-blum.ch").build();

        // 4. Send the request using the BodyInserter.
        // The inserter will create the boundary and set the Content-Type header
        // automatically.
        webClient
            .post()
            .uri("/coach/patients/{patientId}/documents", patientId)
            .header(
                "X-Coach-Key",
                "ThisIsTheDevelopmentCoachAccessKeyItDoesNotHaveToBeSuperSecretButLongEnough")
            .body(BodyInserters.fromMultipartData(body))
            .retrieve()
            .toBodilessEntity()
            .block();
      } catch (Exception e) {
        throw e;
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
