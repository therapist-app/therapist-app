package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;

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
  private final TherapistService therapistService;

  public CounselingPlanPhaseController(CounselingPlanPhaseService counselingPlanPhaseService, TherapistService therapistService) {
    this.counselingPlanPhaseService = counselingPlanPhaseService;
    this.therapistService = therapistService;
  }

  @PostMapping("/")
  public CounselingPlanPhaseOutputDTO createCounselingPlanPhase(
      @RequestBody CreateCounselingPlanPhaseDTO createCounselingPlanPhaseDTO) {
    return counselingPlanPhaseService.createCounselingPlanPhase(createCounselingPlanPhaseDTO);
  }

  @PostMapping("/ai-generated-phase/{counselingPlanId}")
  public CreateCounselingPlanPhaseDTO createCounselingPlanPhaseAIGenerated(
      @PathVariable String counselingPlanId, HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return counselingPlanPhaseService.createCounselingPlanPhaseAIGenerated(counselingPlanId, loggedInTherapist);
  }

  @PostMapping("/ai-generated-exercise/{counselingPlanPhaseId}")
  public CreateExerciseDTO createCounselingPlanExerciseAIGenerated(
      @PathVariable String counselingPlanPhaseId, HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return counselingPlanPhaseService.createCounselingPlanExerciseAIGenerated(
        counselingPlanPhaseId, loggedInTherapist);
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
