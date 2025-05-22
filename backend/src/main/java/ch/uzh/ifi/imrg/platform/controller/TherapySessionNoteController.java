package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import ch.uzh.ifi.imrg.platform.service.MeetingNoteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/therapy-session-notes")
public class TherapySessionNoteController {

  private final Logger logger = LoggerFactory.getLogger(TherapySessionController.class);

  private final MeetingNoteService therapySessionNoteService;
  private final TherapistService therapistService;

  TherapySessionNoteController(
      MeetingNoteService therapySessionNoteService, TherapistService therapistService) {
    this.therapySessionNoteService = therapySessionNoteService;
    this.therapistService = therapistService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public MeetingNoteOutputDTO createTherapySessionNote(
      @RequestBody CreateMeetingNoteDTO createTherapySessionNoteDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    MeetingNoteOutputDTO therapySessionNoteOutputDTO = therapySessionNoteService.createMeetingNote(
        createTherapySessionNoteDTO, loggedInTherapist);
    return therapySessionNoteOutputDTO;
  }

  @GetMapping("/{therapySessionNoteId}")
  @ResponseStatus(HttpStatus.OK)
  public MeetingNoteOutputDTO getTherapySessionNoteById(
      @PathVariable String therapySessionNoteId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    MeetingNoteOutputDTO therapySessionNoteOutputDTO = therapySessionNoteService
        .getMeetingNote(therapySessionNoteId, loggedInTherapist);
    return therapySessionNoteOutputDTO;
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public MeetingNoteOutputDTO updateTherapySessionNote(
      @RequestBody UpdateMeetingNoteDTO updateTherapySessionNoteDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    MeetingNoteOutputDTO therapySessionNoteOutputDTO = therapySessionNoteService.updateTherapySessionNote(
        updateTherapySessionNoteDTO, loggedInTherapist);
    return therapySessionNoteOutputDTO;
  }

  @DeleteMapping("/{therapySessionNoteId}")
  public void deleteTherapySessionById(
      @PathVariable String therapySessionNoteId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    therapySessionNoteService.deleteTherapySessionNoteById(therapySessionNoteId, loggedInTherapist);
  }
}
