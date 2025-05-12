package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapySessionRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapySessionDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO: ensure that a therapist is only allowed to do stuff to their own sessions

@Service
@Transactional
public class TherapySessionService {
  private final Logger logger = LoggerFactory.getLogger(TherapySessionService.class);

  private final TherapySessionRepository therapySessionRepository;
  private final PatientRepository patientRepository;
  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public TherapySessionService(
      @Qualifier("therapySessionRepository") TherapySessionRepository therapySessionRepository,
      @Qualifier("patientRepository") PatientRepository patientRepository) {
    this.therapySessionRepository = therapySessionRepository;
    this.patientRepository = patientRepository;
  }

  public TherapySession createTherapySession(
      CreateTherapySessionDTO createTherapySessionDTO, Therapist loggedInTherapist) {

    Patient patient = patientRepository.getPatientById(createTherapySessionDTO.getPatientId());
    if (!patient.getTherapist().getId().equals(loggedInTherapist.getId())) {
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

  public TherapySession getTherapySession(String therapySessionId, Therapist loggedInTherapist) {

    List<TherapySession> accessibleSessions =
        loggedInTherapist.getPatients().stream()
            .flatMap(patient -> patient.getTherapySessions().stream())
            .collect(Collectors.toList());

    return accessibleSessions.stream()
        .filter(session -> session.getId().equals(therapySessionId))
        .findFirst()
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Therapy session not found or you don't have access to it"));
  }

  public List<TherapySession> getAllTherapySessionsOfPatient(
      String patientId, Therapist loggedInTherapist) {

    Patient patient =
        loggedInTherapist.getPatients().stream()
            .filter(p -> p.getId().equals(patientId))
            .findFirst()
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Patient not found or you don't have access to them"));

    return patient.getTherapySessions();
  }

  public void deleteTherapySessionById(String therapySessionId, Therapist loggedInTherapist) {

    logger.info("Deleting therapy session with ID: " + therapySessionId);
    TherapySession therapySession = getTherapySession(therapySessionId, loggedInTherapist);
    therapySession.getPatient().getTherapySessions().remove(therapySession);
  }
}
