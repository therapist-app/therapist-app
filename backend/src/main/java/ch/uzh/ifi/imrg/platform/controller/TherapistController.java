package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TherapistController {

  private final Logger logger = LoggerFactory.getLogger(TherapistService.class);

  private final TherapistService therapistService;

  TherapistController(TherapistService therapistService) {
    this.therapistService = therapistService;
  }

  @GetMapping("/therapists/me")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO getCurrentlyLoggedInTherapist(HttpServletRequest httpServletRequest) {
    logger.info("/therapists/me");
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(loggedInTherapist).sortDTO();
  }

  @PostMapping("/therapists")
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createTherapist(
      @RequestBody CreateTherapistDTO therapistInputDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("/therapists");
    Therapist therapist =
        TherapistMapper.INSTANCE.convertCreateTherapistDTOtoEntity(therapistInputDTO);
    Therapist createdTherapist =
        therapistService.registerTherapist(therapist, httpServletRequest, httpServletResponse);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(createdTherapist).sortDTO();
  }

  @PostMapping("/therapists/login")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO loginTherapist(
      @RequestBody LoginTherapistDTO loginTherapistDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    logger.info("/therapists/login");
    Therapist loggedInTherapist =
        therapistService.loginTherapist(loginTherapistDTO, httpServletRequest, httpServletResponse);
    System.out.println(loggedInTherapist.getPatients().size());
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(loggedInTherapist).sortDTO();
  }

  @PostMapping("/therapists/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logoutTherapist(HttpServletResponse httpServletResponse) {
    logger.info("/therapists/logout");
    therapistService.logoutTherapist(httpServletResponse);
  }
}
