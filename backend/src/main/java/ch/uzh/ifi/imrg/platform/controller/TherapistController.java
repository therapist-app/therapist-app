package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.DTOMapper;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TherapistController {

  private final TherapistService therapistService;

  TherapistController(TherapistService therapistService) {
    this.therapistService = therapistService;
  }

  @GetMapping("/therapists")
  @ResponseStatus(HttpStatus.OK)
  public List<TherapistOutputDTO> getAllTherapists() {
    List<Therapist> therapists = therapistService.getAllTherapists();
    return therapists.stream().map(DTOMapper.INSTANCE::convertEntityToTherapistOutputDTO).toList();
  }

  @PostMapping("/therapists")
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createTherapist(
      @RequestBody CreateTherapistDTO therapistInputDTO,
      HttpServletResponse httpServletResponseRe) {
    Therapist therapist = DTOMapper.INSTANCE.convertCreateTherapistDTOtoEntity(therapistInputDTO);
    Therapist createdTherapist = therapistService.registerTherapist(therapist, httpServletResponseRe);
    return DTOMapper.INSTANCE.convertEntityToTherapistOutputDTO(createdTherapist);
  }

  @PostMapping("/therapists/login")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO loginTherapist(
      @RequestBody LoginTherapistDTO loginTherapistDTO, HttpServletResponse httpServletResponseRe) {
    Therapist loggedInTherapist = therapistService.loginTherapist(loginTherapistDTO, httpServletResponseRe);
    return DTOMapper.INSTANCE.convertEntityToTherapistOutputDTO(loggedInTherapist);
  }
}
