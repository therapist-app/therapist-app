package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestCreateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestOutputDTO;
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

  //  @GetMapping("/gad7/patient/{patientId}")
  @GetMapping("/patient/{patientId}/psychological-tests/{psychologicalTestName}")
  @ResponseStatus(HttpStatus.OK)
  public List<PsychologicalTestOutputDTO> getTestsForPatient(
      @PathVariable String patientId,
      @CurrentTherapistId String therapistId,
      @PathVariable String psychologicalTestName) {
    return patientTestService.getTestsByPatient(patientId, therapistId, psychologicalTestName);
  }

  //  @PostMapping("/gad7/patient/{patientId}/create")
  @PostMapping("/patient/{patientId}/psychological-tests/{psychologicalTestName}/create")
  @ResponseStatus(HttpStatus.CREATED)
  public PsychologicalTestCreateDTO assignPsychologicalTestToPatient(
      @PathVariable String patientId,
      @Valid @RequestBody PsychologicalTestCreateDTO dto,
      @CurrentTherapistId String therapistId,
      @PathVariable String psychologicalTestName) {
    return patientTestService.assignPsychologicalTest(
        patientId, therapistId, dto, psychologicalTestName);
  }
}
