package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counseling-plans")
public class CounselingPlanController {

  private final CounselingPlanService counselingPlanService;

  public CounselingPlanController(CounselingPlanService counselingPlanService) {
    this.counselingPlanService = counselingPlanService;
  }

  @GetMapping("/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public CounselingPlanOutputDTO getCounselingPlanByPatientId(@PathVariable String patientId) {
    return counselingPlanService.getCounselingPlanByPatientId(patientId);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public CounselingPlanOutputDTO updateCounselingPlan(
      @RequestBody UpdateCounselingPlanDTO updateCounselingPlanDTO) {
    return counselingPlanService.updateCounselingPlan(updateCounselingPlanDTO);
  }
}
