package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanExerciseAIGeneratedOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counseling-plan-phases")
public class CounselingPlanPhaseController {

  private final CounselingPlanPhaseService counselingPlanPhaseService;

  public CounselingPlanPhaseController(CounselingPlanPhaseService counselingPlanPhaseService) {
    this.counselingPlanPhaseService = counselingPlanPhaseService;
  }

  @PostMapping("/")
  public CounselingPlanPhaseOutputDTO createCounselingPlanPhase(
      @RequestBody CreateCounselingPlanPhaseDTO createCounselingPlanPhaseDTO) {
    return counselingPlanPhaseService.createCounselingPlanPhase(createCounselingPlanPhaseDTO);
  }

  @PostMapping("/ai-generated-phase/{counselingPlanId}")
  public CreateCounselingPlanPhaseDTO createCounselingPlanPhaseAIGenerated(
      @PathVariable String counselingPlanId) {
    return counselingPlanPhaseService.createCounselingPlanPhaseAIGenerated(counselingPlanId);
  }

  @PostMapping("/ai-generated-exercise/{counselingPlanPhaseId}")
  public CounselingPlanExerciseAIGeneratedOutputDTO createCounselingPlanExerciseAIGenerated(
      @PathVariable String counselingPlanPhaseId) {
    return counselingPlanPhaseService.createCounselingPlanExerciseAIGenerated(
        counselingPlanPhaseId);
  }

  @PostMapping("/add-exercise")
  public CounselingPlanPhaseOutputDTO addExerciseToCounselingPlanPhase(
      @RequestBody AddExerciseToCounselingPlanPhaseDTO addExerciseToCounselingPlanPhaseDTO) {
    return counselingPlanPhaseService.addExerciseToCounselingPlanPhase(
        addExerciseToCounselingPlanPhaseDTO);
  }

  @PostMapping("/remove-exercise")
  public CounselingPlanPhaseOutputDTO removeExerciseFromCounselingPlanPhase(
      @RequestBody
          RemoveExerciseFromCounselingPlanPhaseDTO removeExerciseFromCounselingPlanPhaseDTO) {
    return counselingPlanPhaseService.removeExerciseFromCounselingPlanPhase(
        removeExerciseFromCounselingPlanPhaseDTO);
  }

  @GetMapping("/{id}")
  public CounselingPlanPhaseOutputDTO getCounselingPlanPhaseById(@PathVariable String id) {
    return counselingPlanPhaseService.getCounselingPlanPhaseById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteCounselingPlanPhase(@PathVariable String id) {
    counselingPlanPhaseService.deleteCounselingPlanPhase(id);
  }
}
