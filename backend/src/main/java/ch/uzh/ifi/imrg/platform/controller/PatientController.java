package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.service.PatientService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @PostMapping("/patients")
  @ResponseStatus(HttpStatus.CREATED)
  public PatientOutputDTO createPatient(@RequestBody CreatePatientDTO patientInputDTO) {
    return patientService.createPatient(patientInputDTO);
  }

  @GetMapping("/patients")
  @ResponseStatus(HttpStatus.OK)
  public List<PatientOutputDTO> getAllPatients() {
    return patientService.getAllPatients();
  }

  @GetMapping("/therapists/{therapistId}/patients")
  @ResponseStatus(HttpStatus.OK)
  public List<PatientOutputDTO> getPatientsForTherapist(@PathVariable String therapistId) {
    return patientService.getPatientsForTherapist(therapistId);
  }

  @GetMapping("/patients/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PatientOutputDTO getPatientById(@PathVariable Long id) {
    return patientService.getPatientById(id);
  }

  @PostMapping("/therapists/{therapistId}/patients")
  @ResponseStatus(HttpStatus.CREATED)
  public PatientOutputDTO createPatientForTherapist(
      @PathVariable String therapistId, @RequestBody CreatePatientDTO inputDTO) {
    return patientService.createPatientForTherapist(therapistId, inputDTO);
  }
}
