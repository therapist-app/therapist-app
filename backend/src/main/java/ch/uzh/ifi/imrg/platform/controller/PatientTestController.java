package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.PatientTestService;
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

  @GetMapping("/gad7/patient/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public List<PsychologicalTestOutputDTO> getTestsForPatient(
      @PathVariable String patientId, @CurrentTherapistId String therapistId) {
    return patientTestService.getTestsByPatient(patientId, therapistId);
  }
}
