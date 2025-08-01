package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.CreateMeetingDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.UpdateMeetingDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
import ch.uzh.ifi.imrg.platform.repository.MeetingRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteSummaryDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.MeetingsMapper;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.Instant;
import java.util.ArrayList;
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

    if (!createMeetingDTO.getMeetingEnd().isAfter(createMeetingDTO.getMeetingStart())) {
      throw new IllegalArgumentException("meetingEnd must be after meetingStart");
    }

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
            .id(createdMeeting.getId())
            .startAt(meeting.getMeetingStart())
            .endAt(meeting.getMeetingEnd())
            .location(meeting.getLocation())
            .meetingStatus(
                CreateMeetingDTOPatientAPI.MeetingStatusEnum.valueOf(
                    createdMeeting.getMeetingStatus().toString()));

    PatientAppAPIs.coachMeetingControllerPatientAPI
        .createMeeting1(createMeetingDTO.getPatientId(), createMeetingDTOPatientAPI)
        .block();

    meetingRepository.flush();
    entityManager.refresh(createdMeeting);

    return createdMeeting;
  }

  public String createMeetingNoteSummary(CreateMeetingNoteSummaryDTO dto, String therapistId) {
    Meeting meeting = meetingRepository.getReferenceById(dto.getMeetingId());

    SecurityUtil.checkOwnership(meeting, therapistId);
    Patient patient = meeting.getPatient();
    String systemPrompt = patient.toLLMContext(0);
    String userPrompt =
        "Create a summary of the following meeting notes (meeting ID: "
            + dto.getMeetingId()
            + "). Do not mention that this is a summary, only provide the summary itself.\n\n"
            + meeting.toLLMContext(0);

    List<ChatMessageDTO> chatMessages = new ArrayList<>();
    chatMessages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    chatMessages.add(new ChatMessageDTO(ChatRole.USER, userPrompt));

    LLMModel llmModel = patient.getTherapist().getLlmModel();
    return LLMFactory.getInstance(llmModel).callLLM(chatMessages, dto.getLanguage());
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

    Instant prospectiveStart =
        dto.getMeetingStart() != null ? dto.getMeetingStart() : meeting.getMeetingStart();

    Instant prospectiveEnd =
        dto.getMeetingEnd() != null ? dto.getMeetingEnd() : meeting.getMeetingEnd();

    if (!prospectiveEnd.isAfter(prospectiveStart)) {
      throw new IllegalArgumentException("meetingEnd must be after meetingStart");
    }

    UpdateMeetingDTOPatientAPI patientAppDto = new UpdateMeetingDTOPatientAPI();

    if (dto.getMeetingStart() != null) {
      meeting.setMeetingStart(dto.getMeetingStart());
      patientAppDto.setStartAt(dto.getMeetingStart());
    }
    if (dto.getMeetingEnd() != null) {
      meeting.setMeetingEnd(dto.getMeetingEnd());
      patientAppDto.setEndAt(dto.getMeetingEnd());
    }
    if (dto.getLocation() != null) {
      meeting.setLocation(dto.getLocation());
      patientAppDto.setLocation(dto.getLocation());
    }
    if (dto.getMeetingStatus() != null) {
      meeting.setMeetingStatus(dto.getMeetingStatus());
      UpdateMeetingDTOPatientAPI.MeetingStatusEnum updateMeetingStatusEnum =
          UpdateMeetingDTOPatientAPI.MeetingStatusEnum.valueOf(dto.getMeetingStatus().name());
      patientAppDto.meetingStatus(updateMeetingStatusEnum);
    }

    PatientAppAPIs.coachMeetingControllerPatientAPI
        .updateMeeting1(meeting.getPatient().getId(), meeting.getId(), patientAppDto)
        .block();

    meetingRepository.save(meeting);

    return MeetingsMapper.INSTANCE.convertEntityToMeetingOutputDTO(meeting);
  }

  public void deleteMeetingById(String meetingId, String therapistId) {
    Meeting meeting = meetingRepository.getReferenceById(meetingId);
    SecurityUtil.checkOwnership(meeting, therapistId);

    meeting.getPatient().getMeetings().remove(meeting);
    PatientAppAPIs.coachMeetingControllerPatientAPI
        .deleteMeeting1(meeting.getPatient().getId(), meetingId)
        .block();
  }
}
