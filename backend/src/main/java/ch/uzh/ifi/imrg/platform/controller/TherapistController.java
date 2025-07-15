package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

  private final TherapistService therapistService;

  TherapistController(TherapistService therapistService) {
    this.therapistService = therapistService;
  }

  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO getCurrentlyLoggedInTherapist(@CurrentTherapistId String therapistId) {
    return therapistService.getTherapistById(therapistId);
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createTherapist(
      @RequestBody CreateTherapistDTO therapistInputDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Therapist therapist =
        TherapistMapper.INSTANCE.convertCreateTherapistDTOtoEntity(therapistInputDTO);
    return therapistService.registerTherapist(therapist, httpServletRequest, httpServletResponse);
  }

  public TherapistOutputDTO updateTherapist(
      @RequestBody UpdateTherapistDTO dto, @CurrentTherapistId String therapistId) {
    return therapistService.updateTherapist(dto, therapistId);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO loginTherapist(
      @RequestBody LoginTherapistDTO loginTherapistDTO,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    return therapistService.loginTherapist(
        loginTherapistDTO, httpServletRequest, httpServletResponse);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logoutTherapist(HttpServletResponse httpServletResponse) {
    therapistService.logoutTherapist(httpServletResponse);
  }
}
