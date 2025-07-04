package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseGoalService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
      @RequestBody CreateCounselingPlanPhaseGoalDTO createCounselingPlanPhaseGoalDTO) {
    return counselingPlanPhaseGoalService.createCounselingPlanPhaseGoal(
        createCounselingPlanPhaseGoalDTO);
  }

  @PostMapping("/{counselingPlanPhaseId}")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateCounselingPlanPhaseGoalDTO createCounselingPlanPhaseGoalAIGenerated(
      @PathVariable String counselingPlanPhaseId, HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return counselingPlanPhaseGoalService.createCounselingPlanPhaseGoalAIGenerated(
        counselingPlanPhaseId, loggedInTherapist);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CounselingPlanPhaseGoalOutputDTO getCounselingPlanPhaseGoalById(@PathVariable String id) {
    return counselingPlanPhaseGoalService.getCounselingPlanPhaseGoalById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCounselingPlanPhaseGoal(@PathVariable String id) {
    counselingPlanPhaseGoalService.deleteCounselingPlanPhaseGoal(id);
  }
}
