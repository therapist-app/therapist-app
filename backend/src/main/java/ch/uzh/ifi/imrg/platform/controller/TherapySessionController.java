package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapySessionDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapySessionOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ChatbotTemplateMapper;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapySessionMapper;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import ch.uzh.ifi.imrg.platform.service.TherapySessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/therapy-sessions")
public class TherapySessionController {

  private final Logger logger = LoggerFactory.getLogger(TherapySessionController.class);

  private final TherapySessionService sessionService;
  private final TherapistService therapistService;

  TherapySessionController(
      TherapySessionService sessionService, TherapistService therapistService) {
    this.sessionService = sessionService;
    this.therapistService = therapistService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public TherapySessionOutputDTO createSession(
      @RequestBody CreateTherapySessionDTO createSessionDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("/therapy-sessions");
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    TherapySession createdSession = sessionService.createTherapySession(createSessionDTO, loggedInTherapist);
    return TherapySessionMapper.INSTANCE.convertEntityToSessionOutputDTO(createdSession);
  }

  @GetMapping("/{therapySessionId}")
  @ResponseStatus(HttpStatus.OK)
  public TherapySessionOutputDTO getTherapySessionById(
      @PathVariable String therapySessionId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("/therapy-sessions/" + therapySessionId);
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    TherapySession therapySession = sessionService.getTherapySession(therapySessionId, loggedInTherapist);
    return TherapySessionMapper.INSTANCE.convertEntityToSessionOutputDTO(therapySession);

  }

  @GetMapping("/patients/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public List<TherapySessionOutputDTO> getTherapySessionsOfPatient(
      @PathVariable String patientId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("/therapy-sessions/patients/" + patientId);
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    List<TherapySession> therapySessions = sessionService.getAllTherapySessionsOfPatient(patientId, loggedInTherapist);
    return therapySessions.stream()
        .map(TherapySessionMapper.INSTANCE::convertEntityToSessionOutputDTO)
        .collect(Collectors.toList());
  }
}
