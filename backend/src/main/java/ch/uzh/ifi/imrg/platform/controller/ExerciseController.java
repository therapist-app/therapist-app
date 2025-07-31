package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.generated.model.ExerciseInformationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.ExerciseService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/exercises")
public class ExerciseController {

  private final ExerciseService exerciseService;

  public ExerciseController(ExerciseService exerciseService) {
    this.exerciseService = exerciseService;
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public ExerciseOutputDTO createExercise(
      @Valid @RequestBody CreateExerciseDTO createExerciseDTO,
      @CurrentTherapistId String therapistId) {
    return exerciseService.createExercise(createExerciseDTO, therapistId);
  }

  @GetMapping("/{exerciseId}")
  @ResponseStatus(HttpStatus.OK)
  public ExerciseOutputDTO getExerciseById(
      @PathVariable String exerciseId, @CurrentTherapistId String therapistId) {
    return exerciseService.getExerciseById(exerciseId, therapistId);
  }

  @GetMapping("/patient/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public List<ExerciseOutputDTO> getAllExercisesOfPatient(
      @PathVariable String patientId, @CurrentTherapistId String therapistId) {
    return exerciseService.getAllExercisesOfPatient(patientId, therapistId);
  }

  @GetMapping("/exercise-information/{exerciseId}")
  @ResponseStatus(HttpStatus.OK)
  public List<ExerciseInformationOutputDTOPatientAPI> getExerciseInformation(
      @PathVariable String exerciseId, @CurrentTherapistId String therapistId) {
    return exerciseService.getExerciseInformation(exerciseId, therapistId);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public ExerciseOutputDTO updateExercise(
      @Valid @RequestBody UpdateExerciseDTO updateExerciseDTO,
      @CurrentTherapistId String therapistId) {
    return exerciseService.updateExercise(updateExerciseDTO, therapistId);
  }

  @DeleteMapping("/{exerciseId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteExercise(
      @PathVariable String exerciseId, @CurrentTherapistId String therapistId) {
    exerciseService.deleteExercise(exerciseId, therapistId);
  }
}
