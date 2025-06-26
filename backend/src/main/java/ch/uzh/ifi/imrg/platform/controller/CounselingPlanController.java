package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counseling-plans")
public class CounselingPlanController {

  private final CounselingPlanService counselingPlanService;

  public CounselingPlanController(CounselingPlanService counselingPlanService) {
    this.counselingPlanService = counselingPlanService;
  }

  @GetMapping("/{patientId}")
  public CounselingPlanOutputDTO getCounselingPlanByPatientId(@RequestParam String patientId) {
    return counselingPlanService.getCounselingPlanByPatientId(patientId);
  }
}
