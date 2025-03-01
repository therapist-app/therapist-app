package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @PostMapping("/therapists/{therapistId}/patients")
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createPatientForTherapist(
      @PathVariable String therapistId, @RequestBody CreatePatientDTO inputDTO) {
    Therapist updatedTherapist = patientService.createPatientForTherapist(therapistId, inputDTO);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
  }
}
