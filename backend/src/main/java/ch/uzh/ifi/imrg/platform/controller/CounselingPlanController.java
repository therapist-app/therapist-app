package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanService;
import jakarta.validation.Valid;
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
  public CounselingPlanOutputDTO getCounselingPlanByPatientId(
      @PathVariable String patientId, @CurrentTherapistId String therapistId) {
    return counselingPlanService.getCounselingPlanByPatientId(patientId, therapistId);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public CounselingPlanOutputDTO updateCounselingPlan(
      @Valid @RequestBody UpdateCounselingPlanDTO updateCounselingPlanDTO,
      @CurrentTherapistId String therapistId) {
    return counselingPlanService.updateCounselingPlan(updateCounselingPlanDTO, therapistId);
  }
}
