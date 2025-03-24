package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.service.PatientService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

  private final Logger logger = LoggerFactory.getLogger(PatientController.class);

  private final PatientService patientService;
  private final TherapistService therapistService;

  public PatientController(PatientService patientService, TherapistService therapistService) {
    this.patientService = patientService;
    this.therapistService = therapistService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createPatientForTherapist(
      @RequestBody CreatePatientDTO inputDTO, HttpServletRequest httpServletRequest) {
    logger.info("/therapists/patients");
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    Therapist updatedTherapist = patientService.createPatientForTherapist(loggedInTherapist.getId(), inputDTO);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePatient(@PathVariable String id) {
    logger.info("/patients/" + id);
    patientService.deletePatient(id);
  }
}
