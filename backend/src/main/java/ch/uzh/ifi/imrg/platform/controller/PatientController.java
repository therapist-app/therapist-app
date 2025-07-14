package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.PatientService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public PatientOutputDTO createPatientForTherapist(
      @RequestBody CreatePatientDTO inputDTO, @CurrentTherapistId String therapistId) {
    return patientService.registerPatient(inputDTO, therapistId);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public List<PatientOutputDTO> getPatientsOfTherapist(@CurrentTherapistId String therapistId) {
    return patientService.getAllPatientsOfTherapist(therapistId);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PatientOutputDTO getPatientById(
      @PathVariable String id, @CurrentTherapistId String therapistId) {
    return patientService.getPatientById(id, therapistId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePatient(@PathVariable String id, @CurrentTherapistId String therapistId) {
    patientService.deletePatient(id, therapistId);
  }
}
