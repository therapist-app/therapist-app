package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanExerciseAIGeneratedDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseAIGeneratedDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
      @Valid @RequestBody CreateCounselingPlanPhaseDTO createCounselingPlanPhaseDTO,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.createCounselingPlanPhase(
        createCounselingPlanPhaseDTO, therapistId);
  }

  @PostMapping("/ai-generated-phase")
  public CreateCounselingPlanPhaseDTO createCounselingPlanPhaseAIGenerated(
      @Valid @RequestBody CreateCounselingPlanPhaseAIGeneratedDTO dto,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.createCounselingPlanPhaseAIGenerated(dto, therapistId);
  }

  @PostMapping("/ai-generated-exercise/{counselingPlanPhaseId}")
  public CreateExerciseDTO createCounselingPlanExerciseAIGenerated(
      @Valid @RequestBody CreateCounselingPlanExerciseAIGeneratedDTO dto,
      @CurrentTherapistId String therapistId) {

    return counselingPlanPhaseService.createCounselingPlanExerciseAIGenerated(dto, therapistId);
  }

  @PostMapping("/add-exercise")
  public CounselingPlanPhaseOutputDTO addExerciseToCounselingPlanPhase(
      @Valid @RequestBody AddExerciseToCounselingPlanPhaseDTO addExerciseToCounselingPlanPhaseDTO,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.addExerciseToCounselingPlanPhase(
        addExerciseToCounselingPlanPhaseDTO, therapistId);
  }

  @PostMapping("/remove-exercise")
  public CounselingPlanPhaseOutputDTO removeExerciseFromCounselingPlanPhase(
      @Valid @RequestBody
          RemoveExerciseFromCounselingPlanPhaseDTO removeExerciseFromCounselingPlanPhaseDTO,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.removeExerciseFromCounselingPlanPhase(
        removeExerciseFromCounselingPlanPhaseDTO, therapistId);
  }

  @GetMapping("/{id}")
  public CounselingPlanPhaseOutputDTO getCounselingPlanPhaseById(
      @PathVariable String id, @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.getCounselingPlanPhaseById(id, therapistId);
  }

  @PutMapping("/")
  public CounselingPlanPhaseOutputDTO updateCounselingPlanPhase(
      @Valid @RequestBody UpdateCounselingPlanPhaseDTO dto,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.updateCounselingPlanPhase(dto, therapistId);
  }

  @DeleteMapping("/{id}")
  public void deleteCounselingPlanPhase(
      @PathVariable String id, @CurrentTherapistId String therapistId) {
    counselingPlanPhaseService.deleteCounselingPlanPhase(id, therapistId);
  }
}
