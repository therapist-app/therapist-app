package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhase;
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

  @PostMapping("/add-exercise")
  public CounselingPlanPhaseOutputDTO addExerciseToCounselingPlanPhase(
      @RequestBody AddExerciseToCounselingPlanPhase addExerciseToCounselingPlanPhaseDTO) {
    return counselingPlanPhaseService.addExerciseToCounselingPlanPhase(
        addExerciseToCounselingPlanPhaseDTO);
  }

  @PostMapping("/remove-exercise")
  public CounselingPlanPhaseOutputDTO removeExerciseFromCounselingPlanPhase(
      @RequestBody RemoveExerciseFromCounselingPlanPhase removeExerciseFromCounselingPlanPhaseDTO) {
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
