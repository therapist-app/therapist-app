package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateGAD7TestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.GAD7TestOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.PatientTestService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
public class PatientTestController {

  private final PatientTestService patientTestService;

  public PatientTestController(PatientTestService patientTestService) {
    this.patientTestService = patientTestService;
  }

  @Valid
  @PostMapping("/gad7")
  @ResponseStatus(HttpStatus.CREATED)
  public GAD7TestOutputDTO createTest(
      @RequestBody CreateGAD7TestDTO createGAD7TestDTO, @CurrentTherapistId String therapistId) {
    return patientTestService.createTest(createGAD7TestDTO, therapistId);
  }

  @GetMapping("/gad7/patient/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public List<GAD7TestOutputDTO> getTestsForPatient(
      @PathVariable String patientId, @CurrentTherapistId String therapistId) {
    return patientTestService.getTestsByPatient(patientId, therapistId);
  }

  @GetMapping("/gad7/{testId}")
  @ResponseStatus(HttpStatus.OK)
  public GAD7TestOutputDTO getTestById(
      @PathVariable String testId, @CurrentTherapistId String therapistId) {
    return patientTestService.getTest(testId, therapistId);
  }
}
