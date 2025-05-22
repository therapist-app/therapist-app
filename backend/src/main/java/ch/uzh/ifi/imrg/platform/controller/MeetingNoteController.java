package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.service.MeetingNoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final Logger logger = LoggerFactory.getLogger(MeetingController.class);

  private final MeetingNoteService meetingNoteService;

  MeetingNoteController(MeetingNoteService meetingNoteService) {
    this.meetingNoteService = meetingNoteService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public MeetingNoteOutputDTO createMeetingNote(
      @RequestBody CreateMeetingNoteDTO createMeetingDTO) {

    MeetingNoteOutputDTO meetingNoteOutputDTO =
        meetingNoteService.createMeetingNote(createMeetingDTO);
    return meetingNoteOutputDTO;
  }

  @GetMapping("/{meetingNoteId}")
  @ResponseStatus(HttpStatus.OK)
  public MeetingNoteOutputDTO getMeetingNoteById(@PathVariable String meetingNoteId) {

    MeetingNoteOutputDTO meetingNoteOutputDTO = meetingNoteService.getMeetingNote(meetingNoteId);
    return meetingNoteOutputDTO;
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public MeetingNoteOutputDTO updateMeetingNote(
      @RequestBody UpdateMeetingNoteDTO updateMeetingNoteDTO) {

    MeetingNoteOutputDTO meetingNoteOutputDTO =
        meetingNoteService.updatemeetingNote(updateMeetingNoteDTO);
    return meetingNoteOutputDTO;
  }

  @DeleteMapping("/{meetingNoteId}")
  public void deleteMeetingNoteById(@PathVariable String meetingNoteId) {
    meetingNoteService.deleteMeetingNoteById(meetingNoteId);
  }
}
