package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalAIGeneratedDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseGoalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counseling-plan-phase-goals")
public class CounselingPlanPhaseGoalController {

  private final CounselingPlanPhaseGoalService counselingPlanPhaseGoalService;

  public CounselingPlanPhaseGoalController(
      CounselingPlanPhaseGoalService counselingPlanPhaseGoalService) {
    this.counselingPlanPhaseGoalService = counselingPlanPhaseGoalService;
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public CounselingPlanPhaseGoalOutputDTO createCounselingPlanPhaseGoal(
      @RequestBody CreateCounselingPlanPhaseGoalDTO createCounselingPlanPhaseGoalDTO,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseGoalService.createCounselingPlanPhaseGoal(
        createCounselingPlanPhaseGoalDTO, therapistId);
  }

  @PostMapping("/{counselingPlanPhaseId}")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateCounselingPlanPhaseGoalDTO createCounselingPlanPhaseGoalAIGenerated(
      @RequestBody CreateCounselingPlanPhaseGoalAIGeneratedDTO dto,
      @CurrentTherapistId String therapistId) {

    return counselingPlanPhaseGoalService.createCounselingPlanPhaseGoalAIGenerated(
        dto, therapistId);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CounselingPlanPhaseGoalOutputDTO getCounselingPlanPhaseGoalById(
      @PathVariable String id, @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseGoalService.getCounselingPlanPhaseGoalById(id, therapistId);
  }

  @PutMapping("/")
  public CounselingPlanPhaseGoalOutputDTO updateCounselingPlanPhaseGoal(
      @RequestBody UpdateCounselingPlanPhaseGoal dto, @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseGoalService.updateCounselingPlanPhaseGoal(dto, therapistId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCounselingPlanPhaseGoal(
      @PathVariable String id, @CurrentTherapistId String therapistId) {
    counselingPlanPhaseGoalService.deleteCounselingPlanPhaseGoal(id, therapistId);
  }
}
