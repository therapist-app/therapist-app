package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapySessionNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateTherapySessionNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapySessionNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import ch.uzh.ifi.imrg.platform.service.TherapySessionNoteService;
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

  private final TherapySessionNoteService therapySessionNoteService;
  private final TherapistService therapistService;

  TherapySessionNoteController(
      TherapySessionNoteService therapySessionNoteService, TherapistService therapistService) {
    this.therapySessionNoteService = therapySessionNoteService;
    this.therapistService = therapistService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public TherapySessionNoteOutputDTO createTherapySessionNote(
      @RequestBody CreateTherapySessionNoteDTO createTherapySessionNoteDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("/therapy-session-notes");
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    TherapySessionNoteOutputDTO therapySessionNoteOutputDTO =
        therapySessionNoteService.createTherapySessionNote(
            createTherapySessionNoteDTO, loggedInTherapist);
    return therapySessionNoteOutputDTO;
  }

  @GetMapping("/{therapySessionNoteId}")
  @ResponseStatus(HttpStatus.OK)
  public TherapySessionNoteOutputDTO getTherapySessionNoteById(
      @PathVariable String therapySessionNoteId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("/therapy-sessions-notes/" + therapySessionNoteId);
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    TherapySessionNoteOutputDTO therapySessionNoteOutputDTO =
        therapySessionNoteService.getTherapySessionNote(therapySessionNoteId, loggedInTherapist);
    return therapySessionNoteOutputDTO;
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public TherapySessionNoteOutputDTO updateTherapySessionNote(
      @RequestBody UpdateTherapySessionNoteDTO updateTherapySessionNoteDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("PUT: /therapy-session-notes");
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    TherapySessionNoteOutputDTO therapySessionNoteOutputDTO =
        therapySessionNoteService.updateTherapySessionNote(
            updateTherapySessionNoteDTO, loggedInTherapist);
    return therapySessionNoteOutputDTO;
  }

  @DeleteMapping("/{therapySessionNoteId}")
  public void deleteTherapySessionById(
      @PathVariable String therapySessionNoteId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("DELETE: /therapy-session-notes/" + therapySessionNoteId);
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    therapySessionNoteService.deleteTherapySessionNoteById(therapySessionNoteId, loggedInTherapist);
  }
}
