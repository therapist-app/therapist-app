package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.CreateMeetingDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
import ch.uzh.ifi.imrg.platform.repository.MeetingRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.MeetingsMapper;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeetingService {

  private final MeetingRepository meetingRepository;
  private final PatientRepository patientRepository;
  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public MeetingService(
      @Qualifier("meetingRepository") MeetingRepository meetingRepository,
      @Qualifier("patientRepository") PatientRepository patientRepository) {
    this.meetingRepository = meetingRepository;
    this.patientRepository = patientRepository;
  }

  public Meeting createMeeting(CreateMeetingDTO createMeetingDTO, String therapistId) {

    Patient patient = patientRepository.getPatientById(createMeetingDTO.getPatientId());
    SecurityUtil.checkOwnership(patient, therapistId);

    Meeting meeting = new Meeting();
    meeting.setMeetingStart(createMeetingDTO.getMeetingStart());
    meeting.setMeetingEnd(createMeetingDTO.getMeetingEnd());
    meeting.setLocation(createMeetingDTO.getLocation());
    meeting.setMeetingStatus(MeetingStatus.CONFIRMED);
    meeting.setPatient(patient);
    Meeting createdMeeting = meetingRepository.save(meeting);
    CreateMeetingDTOPatientAPI createMeetingDTOPatientAPI =
        new CreateMeetingDTOPatientAPI()
            .externalMeetingId(createdMeeting.getId())
            .startAt(meeting.getMeetingStart())
            .endAt(meeting.getMeetingEnd())
            .location(meeting.getLocation());
    PatientAppAPIs.coachMeetingControllerPatientAPI
        .createMeeting1(createMeetingDTO.getPatientId(), createMeetingDTOPatientAPI)
        .block();
    meetingRepository.flush();
    entityManager.refresh(createdMeeting);

    return createdMeeting;
  }

  public MeetingOutputDTO getMeeting(String meetingId, String therapistId) {
    Meeting meeting = meetingRepository.getReferenceById(meetingId);
    SecurityUtil.checkOwnership(meeting, therapistId);

    return MeetingsMapper.INSTANCE.convertEntityToMeetingOutputDTO(meeting);
  }

  public List<MeetingOutputDTO> getAllMeetingsOfPatient(String patientId, String therapistId) {

    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    return patient.getMeetings().stream()
        .map(MeetingsMapper.INSTANCE::convertEntityToMeetingOutputDTO)
        .collect(Collectors.toList());
  }

  public MeetingOutputDTO updateMeeting(UpdateMeetingDTO dto, String therapistId) {
    Meeting meeting = meetingRepository.getReferenceById(dto.getId());
    SecurityUtil.checkOwnership(meeting, therapistId);

    if (dto.getMeetingStart() != null) {
      meeting.setMeetingStart(dto.getMeetingStart());
    }
    if (dto.getMeetingEnd() != null) {
      meeting.setMeetingEnd(dto.getMeetingEnd());
    }
    if (dto.getLocation() != null) {
      meeting.setLocation(dto.getLocation());
    }
    if (dto.getMeetingStatus() != null) {
      meeting.setMeetingStatus(dto.getMeetingStatus());
    }

    meetingRepository.save(meeting);
    return MeetingsMapper.INSTANCE.convertEntityToMeetingOutputDTO(meeting);
  }

  public void deleteMeetingById(String meetingId, String therapistId) {
    Meeting meeting = meetingRepository.getReferenceById(meetingId);
    SecurityUtil.checkOwnership(meeting, therapistId);

    meeting.getPatient().getMeetings().remove(meeting);
    // PatientAppAPIs.coachMeetingControllerPatientAPI
    // .deleteMeeting1(meeting.getPatient().getId(), meetingId)
    // .block();
  }
}
