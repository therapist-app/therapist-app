package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.MeetingRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.MeetingsMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeetingService {
  private final Logger logger = LoggerFactory.getLogger(MeetingService.class);

  private final MeetingRepository meetingRepository;
  private final PatientRepository patientRepository;
  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public MeetingService(
      @Qualifier("meetingRepository") MeetingRepository meetingRepository,
      @Qualifier("patientRepository") PatientRepository patientRepository) {
    this.meetingRepository = meetingRepository;
    this.patientRepository = patientRepository;
  }

  public Meeting createMeeting(CreateMeetingDTO createMeetingDTO) {

    Patient patient = patientRepository.getPatientById(createMeetingDTO.getPatientId());

    Meeting meeting = new Meeting();
    meeting.setMeetingStart(createMeetingDTO.getMeetingStart());
    meeting.setMeetingEnd(createMeetingDTO.getMeetingEnd());
    meeting.setLocation(createMeetingDTO.getLocation());
    meeting.setPatient(patient);
    Meeting createdMeeting = meetingRepository.save(meeting);
    meetingRepository.flush();
    entityManager.refresh(createdMeeting);

    return createdMeeting;
  }

  public MeetingOutputDTO getMeeting(String meetingId) {
    Meeting meeting = meetingRepository.getReferenceById(meetingId);
    return MeetingsMapper.INSTANCE.convertEntityToMeetingOutputDTO(meeting);
  }

  public List<MeetingOutputDTO> getAllMeetingsOfPatient(String patientId) {

    Patient patient = patientRepository.getReferenceById(patientId);

    return patient.getMeetings().stream()
        .map(MeetingsMapper.INSTANCE::convertEntityToMeetingOutputDTO)
        .collect(Collectors.toList());
  }

  public void deleteMeetingById(String meetingId) {
    Meeting meeting = meetingRepository.getReferenceById(meetingId);
    meeting.getPatient().getMeetings().remove(meeting);
  }
}
