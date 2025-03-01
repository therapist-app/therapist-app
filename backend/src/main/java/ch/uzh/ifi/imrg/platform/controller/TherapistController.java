package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TherapistController {

  private final TherapistService therapistService;

  TherapistController(TherapistService therapistService) {
    this.therapistService = therapistService;
  }

  @GetMapping("/therapists/me")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO getCurrentlyLoggedInTherapist(HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(loggedInTherapist).sortDTO();

  }

  @PostMapping("/therapists")
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createTherapist(
      @RequestBody CreateTherapistDTO therapistInputDTO, HttpServletResponse httpServletResponse) {
    Therapist therapist = TherapistMapper.INSTANCE.convertCreateTherapistDTOtoEntity(therapistInputDTO);
    Therapist createdTherapist = therapistService.registerTherapist(therapist, httpServletResponse);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(createdTherapist).sortDTO();
  }

  @PostMapping("/therapists/login")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO loginTherapist(
      @RequestBody LoginTherapistDTO loginTherapistDTO, HttpServletResponse httpServletResponse) {
    Therapist loggedInTherapist = therapistService.loginTherapist(loginTherapistDTO, httpServletResponse);
    System.out.println(loggedInTherapist.getPatients().size());
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(loggedInTherapist).sortDTO();
  }

  @PostMapping("/therapists/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logoutTherapist(
      HttpServletResponse httpServletResponse) {
    therapistService.logoutTherapist(httpServletResponse);
  }
}
