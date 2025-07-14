package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
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
  private final TherapistService therapistService;

  public CounselingPlanPhaseController(
      CounselingPlanPhaseService counselingPlanPhaseService, TherapistService therapistService) {
    this.counselingPlanPhaseService = counselingPlanPhaseService;
    this.therapistService = therapistService;
  }

  @PostMapping("/")
  public CounselingPlanPhaseOutputDTO createCounselingPlanPhase(
      @RequestBody CreateCounselingPlanPhaseDTO createCounselingPlanPhaseDTO, @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.createCounselingPlanPhase(createCounselingPlanPhaseDTO, therapistId);
  }

  @PostMapping("/ai-generated-phase/{counselingPlanId}")
  public CreateCounselingPlanPhaseDTO createCounselingPlanPhaseAIGenerated(
      @PathVariable String counselingPlanId, @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.createCounselingPlanPhaseAIGenerated(
        counselingPlanId, therapistId);
  }

  @PostMapping("/ai-generated-exercise/{counselingPlanPhaseId}")
  public CreateExerciseDTO createCounselingPlanExerciseAIGenerated(
      @PathVariable String counselingPlanPhaseId, @CurrentTherapistId String therapistId) {

    return counselingPlanPhaseService.createCounselingPlanExerciseAIGenerated(
        counselingPlanPhaseId, therapistId);
  }

  @PostMapping("/add-exercise")
  public CounselingPlanPhaseOutputDTO addExerciseToCounselingPlanPhase(
      @RequestBody AddExerciseToCounselingPlanPhaseDTO addExerciseToCounselingPlanPhaseDTO,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.addExerciseToCounselingPlanPhase(
        addExerciseToCounselingPlanPhaseDTO, therapistId);
  }

  @PostMapping("/remove-exercise")
  public CounselingPlanPhaseOutputDTO removeExerciseFromCounselingPlanPhase(
      @RequestBody RemoveExerciseFromCounselingPlanPhaseDTO removeExerciseFromCounselingPlanPhaseDTO,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.removeExerciseFromCounselingPlanPhase(
        removeExerciseFromCounselingPlanPhaseDTO, therapistId);
  }

  @GetMapping("/{id}")
  public CounselingPlanPhaseOutputDTO getCounselingPlanPhaseById(@PathVariable String id,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.getCounselingPlanPhaseById(id, therapistId);
  }

  @PutMapping("/{id}")
  public CounselingPlanPhaseOutputDTO updateCounselingPlanPhase(
      @PathVariable String id, @RequestBody CreateCounselingPlanPhaseDTO updateDto,
      @CurrentTherapistId String therapistId) {
    return counselingPlanPhaseService.updateCounselingPlanPhase(id, updateDto, therapistId);
  }

  @DeleteMapping("/{id}")
  public void deleteCounselingPlanPhase(@PathVariable String id, @CurrentTherapistId String therapistId) {
    counselingPlanPhaseService.deleteCounselingPlanPhase(id, therapistId);
  }
}
