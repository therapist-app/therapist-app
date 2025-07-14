package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.MeetingNote;
import ch.uzh.ifi.imrg.platform.repository.MeetingNoteRepository;
import ch.uzh.ifi.imrg.platform.repository.MeetingRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
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

@Service
@Transactional
public class MeetingNoteService {
  private final Logger logger = LoggerFactory.getLogger(MeetingService.class);

  private final MeetingNoteRepository meetingNoteRepository;
  private final MeetingRepository meetingRepository;
  private final PatientRepository patientRepository;
  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public MeetingNoteService(
      @Qualifier("meetingNoteRepository") MeetingNoteRepository meetingNoteRepository,
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("meetingRepository") MeetingRepository meetingRepository) {
    this.meetingNoteRepository = meetingNoteRepository;
    this.meetingRepository = meetingRepository;
    this.patientRepository = patientRepository;
  }

  public MeetingNoteOutputDTO createMeetingNote(CreateMeetingNoteDTO createMeetingNoteDTO, String therapistId) {

    Meeting meeting = this.meetingRepository.getReferenceById(createMeetingNoteDTO.getMeetingId());
    SecurityUtil.checkOwnership(meeting, therapistId);

    MeetingNote meetingNote = new MeetingNote();
    meetingNote.setTitle(createMeetingNoteDTO.getTitle());
    meetingNote.setContent(createMeetingNoteDTO.getContent());

    meetingNote.setMeeting(meeting);
    meetingNoteRepository.save(meetingNote);

    MeetingNoteOutputDTO meetingNoteOutputDTO = new MeetingNoteOutputDTO(
        meetingNote.getId(),
        meeting.getCreatedAt(),
        meeting.getUpdatedAt(),
        meetingNote.getTitle(),
        meetingNote.getContent());

    return meetingNoteOutputDTO;
  }

  public MeetingNoteOutputDTO getMeetingNote(String meetingNoteId, String therapistId) {

    MeetingNote meetingNote = meetingNoteRepository.getReferenceById(meetingNoteId);
    SecurityUtil.checkOwnership(meetingNote, therapistId);

    MeetingNoteOutputDTO meetingNoteOutputDTO = new MeetingNoteOutputDTO(
        meetingNote.getId(),
        meetingNote.getCreatedAt(),
        meetingNote.getUpdatedAt(),
        meetingNote.getTitle(),
        meetingNote.getContent());

    return meetingNoteOutputDTO;
  }

  public List<MeetingNoteOutputDTO> getAllmeetingsNotesOfmeeting(String meetingId, String therapistId) {

    Meeting meeting = this.meetingRepository.getReferenceById(meetingId);
    SecurityUtil.checkOwnership(meeting, therapistId);

    List<MeetingNoteOutputDTO> meetingNoteOutputDTOs = new ArrayList<>();

    for (MeetingNote meetingNote : meeting.getMeetingNotes()) {
      MeetingNoteOutputDTO meetingNoteOutputDTO = new MeetingNoteOutputDTO(
          meetingNote.getId(),
          meetingNote.getCreatedAt(),
          meetingNote.getUpdatedAt(),
          meetingNote.getTitle(),
          meetingNote.getContent());

      meetingNoteOutputDTOs.add(meetingNoteOutputDTO);
    }

    return meetingNoteOutputDTOs;
  }

  public MeetingNoteOutputDTO updatemeetingNote(UpdateMeetingNoteDTO updatemeetingNoteDTO, String therapistId) {
    MeetingNote meetingNote = meetingNoteRepository.getReferenceById(updatemeetingNoteDTO.getId());
    SecurityUtil.checkOwnership(meetingNote, therapistId);

    if (updatemeetingNoteDTO.getTitle() != null) {
      meetingNote.setTitle(updatemeetingNoteDTO.getTitle());
    }

    if (updatemeetingNoteDTO.getContent() != null) {
      meetingNote.setContent(updatemeetingNoteDTO.getContent());
    }

    meetingNoteRepository.save(meetingNote);

    MeetingNoteOutputDTO meetingNoteOutputDTO = new MeetingNoteOutputDTO(
        meetingNote.getId(),
        meetingNote.getCreatedAt(),
        meetingNote.getUpdatedAt(),
        meetingNote.getTitle(),
        meetingNote.getContent());

    return meetingNoteOutputDTO;
  }

  public void deleteMeetingNoteById(String meetingNoteId, String therapistId) {
    MeetingNote meetingNote = meetingNoteRepository.getReferenceById(meetingNoteId);
    SecurityUtil.checkOwnership(meetingNote, therapistId);

    meetingNote.getMeeting().getMeetingNotes().remove(meetingNote);
  }
}
