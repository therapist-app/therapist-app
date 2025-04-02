package ch.uzh.ifi.imrg.platform.service;

import jakarta.transaction.Transactional;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.PatientDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;

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

    public void uploadPatientDocument(String patientId, MultipartFile file, Therapist loggedInTherapist) {

        Patient patient = patientRepository.findById(patientId)
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
}
