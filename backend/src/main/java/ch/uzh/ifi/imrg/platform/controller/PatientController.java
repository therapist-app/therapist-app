package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientMapper;
import ch.uzh.ifi.imrg.platform.service.PatientService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
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
  public PatientOutputDTO createPatientForTherapist(
      @RequestBody CreatePatientDTO inputDTO, HttpServletRequest httpServletRequest) {
    logger.info("/patients");
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    Patient registeredPatient = patientService.registerPatient(loggedInTherapist.getId(), inputDTO);
    return PatientMapper.INSTANCE.convertEntityToPatientOutputDTO(registeredPatient);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public List<PatientOutputDTO> getPatientsOfTherapist(
      HttpServletRequest httpServletRequest) {
    logger.info("/patients");
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    List<Patient> patients = patientService.getAllPatientsOfTherapist(loggedInTherapist);
    return patients.stream()
        .map(PatientMapper.INSTANCE::convertEntityToPatientOutputDTO)
        .collect(Collectors.toList());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePatient(@PathVariable String id) {
    logger.info("/patients/" + id);
    patientService.deletePatient(id);
  }
}
