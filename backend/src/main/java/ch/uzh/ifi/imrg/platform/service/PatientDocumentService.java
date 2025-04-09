package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.PatientDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientDocumentMapper;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class PatientDocumentService {

  private final Logger logger = LoggerFactory.getLogger(PatientDocumentService.class);

  private final PatientRepository patientRepository;
  private final TherapistRepository therapistRepository;
  private final PatientDocumentRepository patientDocumentRepository;

  public PatientDocumentService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository,
      @Qualifier("patientDocumentRepository") PatientDocumentRepository patientDocumentRepository) {
    this.patientRepository = patientRepository;
    this.therapistRepository = therapistRepository;
    this.patientDocumentRepository = patientDocumentRepository;
  }

  public void uploadPatientDocument(
      String patientId, MultipartFile file, Therapist loggedInTherapist) {

    Patient patient = patientRepository
        .findById(patientId)
        .orElseThrow(() -> new RuntimeException("Patient not found"));

    String extractedText = DocumentParserUtil.extractText(file);
    PatientDocument patientDocument = new PatientDocument();
    patientDocument.setPatient(patient);
    patientDocument.setFileName(file.getOriginalFilename());
    patientDocument.setFileType(file.getContentType());
    try {
      patientDocument.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file bytes", e);
    }
    patientDocument.setExtractedText(extractedText);

    patientDocumentRepository.save(patientDocument);
  }

  public List<PatientDocumentOutputDTO> getDocumentsOfPatient(String patientId, Therapist loggedInTherapist) {
    boolean exists = patientRepository.existsByIdAndTherapistId(patientId, loggedInTherapist.getId());
    if (!exists) {
      throw new EntityNotFoundException("Patient not found or doesn't belong to therapist");
    }

    Patient patient = patientRepository.getPatientById(patientId);
    System.out.println("Size of patient documents" + patient.getPatientDocuments().size());
    return patient.getPatientDocuments().stream()
        .map(PatientDocumentMapper.INSTANCE::convertEntityToPatientDocumentOutputDTO)
        .collect(Collectors.toList());
  }

  public PatientDocument downloadPatientDocument(
      String patientDocumentId, Therapist loggedInTherapist) {

    PatientDocument patientDocument = patientDocumentRepository
        .findById(patientDocumentId)
        .orElseThrow(() -> new RuntimeException("Patient document not found"));

    return patientDocument;
  }
}
