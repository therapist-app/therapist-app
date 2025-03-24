package ch.uzh.ifi.imrg.platform.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapySessionRepository;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapySessionDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class TherapySessionService {
    private final Logger logger = LoggerFactory.getLogger(TherapySessionService.class);

    private final TherapySessionRepository therapySessionRepository;
    private final PatientRepository patientRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TherapySessionService(
            @Qualifier("therapySessionRepository") TherapySessionRepository therapySessionRepository,
            @Qualifier("patientRepository") PatientRepository patientRepository) {
        this.therapySessionRepository = therapySessionRepository;
        this.patientRepository = patientRepository;
    }

    public TherapySession createTherapySession(CreateTherapySessionDTO createTherapySessionDTO,
            Therapist loggedInTherapist) {

        Patient patient = patientRepository.getPatientById(createTherapySessionDTO.getPatientId());
        if (patient.getTherapist().getId() != loggedInTherapist.getId()) {
            throw new Error("Cannot create a therapy session for a patient that does not belong to you");
        }

        TherapySession therapySession = new TherapySession();
        therapySession.setSessionStart(createTherapySessionDTO.getSessionStart());
        therapySession.setSessionEnd(createTherapySessionDTO.getSessionEnd());
        therapySession.setPatient(patient);
        TherapySession createdTherapySession = therapySessionRepository.save(therapySession);
        therapySessionRepository.flush();
        entityManager.refresh(createdTherapySession);

        return createdTherapySession;
    }

}
