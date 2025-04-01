package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateGAD7TestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.GAD7TestOutputDTO;
import ch.uzh.ifi.imrg.platform.service.PatientTestService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
public class PatientTestController {

  private final Logger logger = LoggerFactory.getLogger(PatientTestController.class);
  private final PatientTestService patientTestService;

  public PatientTestController(PatientTestService patientTestService) {
    this.patientTestService = patientTestService;
  }

  @Valid
  @PostMapping("/gad7")
  @ResponseStatus(HttpStatus.CREATED)
  public GAD7TestOutputDTO createTest(@RequestBody CreateGAD7TestDTO createGAD7TestDTO) {
    logger.info("Creating new GAD7 test");
    return patientTestService.createTest(createGAD7TestDTO);
  }

/*   @GetMapping("/gad7/{testId}")
  @ResponseStatus(HttpStatus.OK)
  public GAD7TestOutputDTO getTest(@PathVariable String testId) {
    logger.info("Retrieving GAD7 test with id: " + testId);
    return patientTestService.getTest(testId);
  } */
}
