package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.GAD7Test;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.repository.GAD7Repository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapySessionRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateGAD7TestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.GAD7TestOutputDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PatientTestService {

    private final GAD7Repository gad7Repository;
    private final PatientRepository patientRepository;
    private final TherapySessionRepository therapySessionRepository;

    public PatientTestService(GAD7Repository gad7Repository, PatientRepository patientRepository, TherapySessionRepository therapySessionRepository) {
        this.gad7Repository = gad7Repository;
        this.patientRepository = patientRepository;
        this.therapySessionRepository = therapySessionRepository;
    }

    public GAD7TestOutputDTO createTest(CreateGAD7TestDTO dto) {
        Patient patient = patientRepository.getPatientById(dto.getPatientId());
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found with id: " + dto.getPatientId());
        }

        TherapySession session = therapySessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Therapy session not found with id: " + dto.getSessionId()));

        GAD7Test test = new GAD7Test();
        test.setPatient(patient);
        test.setTherapySession(session);
        test.setQuestion1(dto.getQuestion1());
        test.setQuestion2(dto.getQuestion2());
        test.setQuestion3(dto.getQuestion3());
        test.setQuestion4(dto.getQuestion4());
        test.setQuestion5(dto.getQuestion5());
        test.setQuestion6(dto.getQuestion6());
        test.setQuestion7(dto.getQuestion7());

        GAD7Test savedTest = gad7Repository.save(test);

        GAD7TestOutputDTO outputDTO = new GAD7TestOutputDTO();
        outputDTO.setTestId(savedTest.getTestId());
        outputDTO.setPatientId(savedTest.getPatient().getId());
        outputDTO.setSessionId(savedTest.getTherapySession().getId());
        outputDTO.setCreationDate(savedTest.getCreationDate());
        outputDTO.setQuestion1(savedTest.getQuestion1());
        outputDTO.setQuestion2(savedTest.getQuestion2());
        outputDTO.setQuestion3(savedTest.getQuestion3());
        outputDTO.setQuestion4(savedTest.getQuestion4());
        outputDTO.setQuestion5(savedTest.getQuestion5());
        outputDTO.setQuestion6(savedTest.getQuestion6());
        outputDTO.setQuestion7(savedTest.getQuestion7());
        
        return outputDTO;

    }

/*     public GAD7TestOutputDTO getTest(String testId) {
        GAD7Test test =
                gad7Repository
                        .findById(testId)
                        .orElseThrow(() -> new RuntimeException("GAD7 test not found"));
        GAD7TestOutputDTO outputDTO = new GAD7TestOutputDTO();
        outputDTO.setTestId(test.getTestId());
        outputDTO.setPatientId(test.getPatientId());
        outputDTO.setCreationDate(test.getCreationDate());
        outputDTO.setSessionId(test.getSessionId());
        outputDTO.setQuestion1(test.getQuestion1());
        outputDTO.setQuestion2(test.getQuestion2());
        outputDTO.setQuestion3(test.getQuestion3());
        outputDTO.setQuestion4(test.getQuestion4());
        outputDTO.setQuestion5(test.getQuestion5());
        outputDTO.setQuestion6(test.getQuestion6());
        outputDTO.setQuestion7(test.getQuestion7());
        return outputDTO;
    } */
}
