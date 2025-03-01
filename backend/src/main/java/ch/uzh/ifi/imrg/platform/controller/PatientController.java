package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.service.PatientService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientController {

  private final PatientService patientService;
  private final TherapistService therapistService;

  public PatientController(PatientService patientService, TherapistService therapistService) {
    this.patientService = patientService;
    this.therapistService = therapistService;
  }

  @PostMapping("/therapists/patients")
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createPatientForTherapist(
      @RequestBody CreatePatientDTO inputDTO, HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    Therapist updatedTherapist = patientService.createPatientForTherapist(loggedInTherapist.getId(), inputDTO);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
  }
}
