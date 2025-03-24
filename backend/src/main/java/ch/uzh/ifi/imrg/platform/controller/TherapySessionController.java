package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapySessionDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapySessionOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapySessionMapper;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import ch.uzh.ifi.imrg.platform.service.TherapySessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TherapySessionController {

  private final Logger logger = LoggerFactory.getLogger(TherapySessionController.class);

  private final TherapySessionService sessionService;
  private final TherapistService therapistService;

  TherapySessionController(
      TherapySessionService sessionService, TherapistService therapistService) {
    this.sessionService = sessionService;
    this.therapistService = therapistService;
  }

  @PostMapping("/sessions")
  @ResponseStatus(HttpStatus.CREATED)
  public TherapySessionOutputDTO createSession(
      @RequestBody CreateTherapySessionDTO createSessionDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("/sessions");
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    TherapySession createdSession =
        sessionService.createTherapySession(createSessionDTO, loggedInTherapist);
    return TherapySessionMapper.INSTANCE.convertEntityToSessionOutputDTO(createdSession);
  }
}
