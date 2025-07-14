package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseGoalService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
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
  private final TherapistService therapistService;

  public CounselingPlanPhaseGoalController(
      CounselingPlanPhaseGoalService counselingPlanPhaseGoalService,
      TherapistService therapistService) {
    this.counselingPlanPhaseGoalService = counselingPlanPhaseGoalService;
    this.therapistService = therapistService;
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
      @PathVariable String counselingPlanPhaseId, @CurrentTherapistId String therapistId) {

    return counselingPlanPhaseGoalService.createCounselingPlanPhaseGoalAIGenerated(
        counselingPlanPhaseId, therapistId);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CounselingPlanPhaseGoalOutputDTO getCounselingPlanPhaseGoalById(
      @PathVariable String id, @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseGoalService.getCounselingPlanPhaseGoalById(id, therapistId);
  }

  @PutMapping("/{id}")
  public CounselingPlanPhaseGoalOutputDTO updateCounselingPlanPhase(
      @PathVariable String id,
      @RequestBody CreateCounselingPlanPhaseGoalDTO updateDto,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseGoalService.updateCounselingPlanPhaseGoal(id, updateDto, therapistId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCounselingPlanPhaseGoal(
      @PathVariable String id, @CurrentTherapistId String therapistId) {
    counselingPlanPhaseGoalService.deleteCounselingPlanPhaseGoal(id, therapistId);
  }
}
