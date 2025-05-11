package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.entity.TherapySessionNote;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapySessionNoteRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapySessionRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapySessionNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateTherapySessionNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapySessionNoteOutputDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO: for all endpoints: check if therapy session belongs to therapist

@Service
@Transactional
public class TherapySessionNoteService {
  private final Logger logger = LoggerFactory.getLogger(TherapySessionService.class);

  private final TherapySessionNoteRepository therapySessionNoteRepository;
  private final TherapySessionRepository therapySessionRepository;
  private final PatientRepository patientRepository;
  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public TherapySessionNoteService(
      @Qualifier("therapySessionNoteRepository")
          TherapySessionNoteRepository therapySessionNoteRepository,
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapySessionRepository") TherapySessionRepository therapySessionRepository) {
    this.therapySessionNoteRepository = therapySessionNoteRepository;
    this.therapySessionRepository = therapySessionRepository;
    this.patientRepository = patientRepository;
  }

  public TherapySessionNoteOutputDTO createTherapySessionNote(
      CreateTherapySessionNoteDTO createTherapySessionNoteDTO, Therapist loggedInTherapist) {

    TherapySession therapySession =
        this.therapySessionRepository.getReferenceById(
            createTherapySessionNoteDTO.getTherapySessionId());
    if (therapySession == null) {
      throw new EntityNotFoundException(
          "The therapy session with ID: "
              + createTherapySessionNoteDTO.getTherapySessionId()
              + " was not found.");
    }

    TherapySessionNote therapySessionNote = new TherapySessionNote();
    therapySessionNote.setTitle(createTherapySessionNoteDTO.getTitle());
    therapySessionNote.setContent(createTherapySessionNoteDTO.getContent());

    therapySessionNote.setTherapySession(therapySession);
    therapySessionNoteRepository.save(therapySessionNote);

    TherapySessionNoteOutputDTO therapySessionNoteOutputDTO =
        new TherapySessionNoteOutputDTO(
            therapySessionNote.getId(),
            therapySession.getCreatedAt(),
            therapySession.getUpdatedAt(),
            therapySessionNote.getTitle(),
            therapySessionNote.getContent());

    return therapySessionNoteOutputDTO;
  }

  public TherapySessionNoteOutputDTO getTherapySessionNote(
      String therapySessionNoteId, Therapist loggedInTherapist) {

    TherapySessionNote therapySessionNote =
        therapySessionNoteRepository.getReferenceById(therapySessionNoteId);

    TherapySessionNoteOutputDTO therapySessionNoteOutputDTO =
        new TherapySessionNoteOutputDTO(
            therapySessionNote.getId(),
            therapySessionNote.getCreatedAt(),
            therapySessionNote.getUpdatedAt(),
            therapySessionNote.getTitle(),
            therapySessionNote.getContent());

    return therapySessionNoteOutputDTO;
  }

  public List<TherapySessionNoteOutputDTO> getAllTherapySessionsNotesOfTherapySession(
      String therappySessionId, Therapist loggedInTherapist) {

    TherapySession therapySession =
        this.therapySessionRepository.getReferenceById(therappySessionId);
    if (therapySession == null) {
      throw new EntityNotFoundException(
          "The therapy session with ID: " + therappySessionId + " was not found.");
    }

    List<TherapySessionNoteOutputDTO> therapySessionNoteOutputDTOs = new ArrayList<>();

    for (TherapySessionNote therapySessionNote : therapySession.getTherapySessionNotes()) {
      TherapySessionNoteOutputDTO therapySessionNoteOutputDTO =
          new TherapySessionNoteOutputDTO(
              therapySessionNote.getId(),
              therapySessionNote.getCreatedAt(),
              therapySessionNote.getUpdatedAt(),
              therapySessionNote.getTitle(),
              therapySessionNote.getContent());

      therapySessionNoteOutputDTOs.add(therapySessionNoteOutputDTO);
    }

    return therapySessionNoteOutputDTOs;
  }

  public TherapySessionNoteOutputDTO updateTherapySessionNote(
      UpdateTherapySessionNoteDTO updateTherapySessionNoteDTO, Therapist loggedInTherapist) {
    TherapySessionNote therapySessionNote =
        therapySessionNoteRepository.getReferenceById(updateTherapySessionNoteDTO.getId());

    if (updateTherapySessionNoteDTO.getTitle() != null) {
      therapySessionNote.setTitle(updateTherapySessionNoteDTO.getTitle());
    }

    if (updateTherapySessionNoteDTO.getContent() != null) {
      therapySessionNote.setContent(updateTherapySessionNoteDTO.getContent());
    }

    therapySessionNoteRepository.save(therapySessionNote);

    TherapySessionNoteOutputDTO therapySessionNoteOutputDTO =
        new TherapySessionNoteOutputDTO(
            therapySessionNote.getId(),
            therapySessionNote.getCreatedAt(),
            therapySessionNote.getUpdatedAt(),
            therapySessionNote.getTitle(),
            therapySessionNote.getContent());

    return therapySessionNoteOutputDTO;
  }

  public void deleteTherapySessionNoteById(
      String therapySessionNoteId, Therapist loggedInTherapist) {
    therapySessionNoteRepository.deleteById(therapySessionNoteId);
  }
}
