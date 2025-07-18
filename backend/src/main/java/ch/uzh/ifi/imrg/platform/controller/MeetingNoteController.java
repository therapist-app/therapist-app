package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.MeetingNoteService;
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
@RequestMapping("/meeting-notes")
public class MeetingNoteController {

  private final MeetingNoteService meetingNoteService;

  MeetingNoteController(MeetingNoteService meetingNoteService) {
    this.meetingNoteService = meetingNoteService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public MeetingNoteOutputDTO createMeetingNote(
      @RequestBody CreateMeetingNoteDTO createMeetingDTO, @CurrentTherapistId String therapistId) {

    MeetingNoteOutputDTO meetingNoteOutputDTO =
        meetingNoteService.createMeetingNote(createMeetingDTO, therapistId);
    return meetingNoteOutputDTO;
  }

  @GetMapping("/{meetingNoteId}")
  @ResponseStatus(HttpStatus.OK)
  public MeetingNoteOutputDTO getMeetingNoteById(
      @PathVariable String meetingNoteId, @CurrentTherapistId String therapistId) {

    MeetingNoteOutputDTO meetingNoteOutputDTO =
        meetingNoteService.getMeetingNote(meetingNoteId, therapistId);
    return meetingNoteOutputDTO;
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public MeetingNoteOutputDTO updateMeetingNote(
      @RequestBody UpdateMeetingNoteDTO updateMeetingNoteDTO,
      @CurrentTherapistId String therapistId) {

    MeetingNoteOutputDTO meetingNoteOutputDTO =
        meetingNoteService.updatemeetingNote(updateMeetingNoteDTO, therapistId);
    return meetingNoteOutputDTO;
  }

  @DeleteMapping("/{meetingNoteId}")
  public void deleteMeetingNoteById(
      @PathVariable String meetingNoteId, @CurrentTherapistId String therapistId) {
    meetingNoteService.deleteMeetingNoteById(meetingNoteId, therapistId);
  }
}
