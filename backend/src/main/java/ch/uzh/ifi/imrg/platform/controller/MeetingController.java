package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteSummaryDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.MeetingsMapper;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.MeetingService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

  private final MeetingService meetingService;

  MeetingController(MeetingService meetingService) {
    this.meetingService = meetingService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public MeetingOutputDTO createMeeting(
      @Valid @RequestBody CreateMeetingDTO createMeetingDTO,
      @CurrentTherapistId String therapistId) {

    Meeting createdMeeting = meetingService.createMeeting(createMeetingDTO, therapistId);
    return MeetingsMapper.INSTANCE.convertEntityToMeetingOutputDTO(createdMeeting);
  }

  @PostMapping("/meeting-note-summary")
  @ResponseStatus(HttpStatus.CREATED)
  public String createMeetingNoteSummary(
      @Valid @RequestBody CreateMeetingNoteSummaryDTO dto, @CurrentTherapistId String therapistId) {
    return meetingService.createMeetingNoteSummary(dto, therapistId);
  }

  @GetMapping("/{meetingId}")
  @ResponseStatus(HttpStatus.OK)
  public MeetingOutputDTO getMeetingById(
      @PathVariable String meetingId, @CurrentTherapistId String therapistId) {

    MeetingOutputDTO meetingOutputDTO = meetingService.getMeeting(meetingId, therapistId);
    return meetingOutputDTO;
  }

  @GetMapping("/patients/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public List<MeetingOutputDTO> getMeetingsOfPatient(
      @PathVariable String patientId, @CurrentTherapistId String therapistId) {

    List<MeetingOutputDTO> meetingOutputDTOs =
        meetingService.getAllMeetingsOfPatient(patientId, therapistId);
    return meetingOutputDTOs;
  }

  @PutMapping()
  @ResponseStatus(HttpStatus.OK)
  public MeetingOutputDTO updateMeeting(
      @Valid @RequestBody UpdateMeetingDTO dto, @CurrentTherapistId String therapistId) {
    return meetingService.updateMeeting(dto, therapistId);
  }

  @DeleteMapping("/{meetingId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteMeetingById(
      @PathVariable String meetingId, @CurrentTherapistId String therapistId) {
    meetingService.deleteMeetingById(meetingId, therapistId);
  }
}
