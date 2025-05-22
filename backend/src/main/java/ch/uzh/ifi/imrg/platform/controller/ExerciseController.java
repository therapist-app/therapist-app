package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import ch.uzh.ifi.imrg.platform.service.ExerciseService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

  private final ExerciseService exerciseService;

  public ExerciseController(ExerciseService exerciseService) {
    this.exerciseService = exerciseService;
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public ExerciseOutputDTO createExercise(@RequestBody CreateExerciseDTO createExerciseDTO) {
    return exerciseService.createExercise(createExerciseDTO);
  }

  @GetMapping("/{exerciseId}")
  @ResponseStatus(HttpStatus.OK)
  public ExerciseOutputDTO getExerciseById(@PathVariable String exerciseId) {
    return exerciseService.getExerciseById(exerciseId);
  }

  @GetMapping("/patient/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public List<ExerciseOutputDTO> getAllExercisesOfPatient(
      @PathVariable String patientId) {
    return exerciseService.getAllExercisesOfPatient(patientId);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public ExerciseOutputDTO updateExercise(@RequestBody UpdateExerciseDTO updateExerciseDTO) {
    return exerciseService.updateExercise(updateExerciseDTO);
  }

  @DeleteMapping("/{exerciseId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteExercise(@PathVariable String exerciseId) {
    exerciseService.deleteExercise(exerciseId);
  }
}
