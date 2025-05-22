package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.MeetingsMapper;
import ch.uzh.ifi.imrg.platform.service.MeetingService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  private final MeetingService sessionService;
  private final TherapistService therapistService;

  TherapySessionController(MeetingService sessionService, TherapistService therapistService) {
    this.sessionService = sessionService;
    this.therapistService = therapistService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public MeetingOutputDTO createSession(
      @RequestBody CreateMeetingDTO createSessionDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    Meeting createdSession =
        sessionService.createTherapySession(createSessionDTO, loggedInTherapist);
    return MeetingsMapper.INSTANCE.convertEntityToMeetingOutputDTO(createdSession);
  }

  @GetMapping("/{therapySessionId}")
  @ResponseStatus(HttpStatus.OK)
  public MeetingOutputDTO getTherapySessionById(
      @PathVariable String therapySessionId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    Meeting therapySession = sessionService.getTherapySession(therapySessionId, loggedInTherapist);
    return MeetingsMapper.INSTANCE.convertEntityToMeetingOutputDTO(therapySession);
  }

  @GetMapping("/patients/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public List<MeetingOutputDTO> getTherapySessionsOfPatient(
      @PathVariable String patientId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    List<Meeting> therapySessions =
        sessionService.getAllTherapySessionsOfPatient(patientId, loggedInTherapist);
    return therapySessions.stream()
        .map(MeetingsMapper.INSTANCE::convertEntityToMeetingOutputDTO)
        .collect(Collectors.toList());
  }

  @DeleteMapping("/{therapySessionId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTherapySessionById(
      @PathVariable String therapySessionId,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

    sessionService.deleteTherapySessionById(therapySessionId, loggedInTherapist);
  }
}
