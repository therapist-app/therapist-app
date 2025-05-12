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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/therapists")
public class TherapistController {

  private final Logger logger = LoggerFactory.getLogger(TherapistController.class);

  private final TherapistService therapistService;

  TherapistController(TherapistService therapistService) {
    this.therapistService = therapistService;
  }

  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO getCurrentlyLoggedInTherapist(HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(loggedInTherapist).sortDTO();
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createTherapist(
      @RequestBody CreateTherapistDTO therapistInputDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist therapist =
        TherapistMapper.INSTANCE.convertCreateTherapistDTOtoEntity(therapistInputDTO);
    Therapist createdTherapist =
        therapistService.registerTherapist(therapist, httpServletRequest, httpServletResponse);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(createdTherapist).sortDTO();
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO loginTherapist(
      @RequestBody LoginTherapistDTO loginTherapistDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist =
        therapistService.loginTherapist(loginTherapistDTO, httpServletRequest, httpServletResponse);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(loggedInTherapist).sortDTO();
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logoutTherapist(HttpServletResponse httpServletResponse) {
    therapistService.logoutTherapist(httpServletResponse);
  }
}
