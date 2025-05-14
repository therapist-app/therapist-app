package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseTextDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseTextDTO;
import ch.uzh.ifi.imrg.platform.service.ExerciseTextService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise-texts")
public class ExerciseTextController {
  private final Logger logger = LoggerFactory.getLogger(ExerciseTextController.class);

  private final ExerciseTextService exerciseTextService;

  public ExerciseTextController(ExerciseTextService exerciseTextService) {
    this.exerciseTextService = exerciseTextService;
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public void createExerciseText(@RequestBody CreateExerciseTextDTO createExerciseTextDTO) {
    exerciseTextService.createExerciseText(createExerciseTextDTO);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public void updateExerciseText(@RequestBody UpdateExerciseTextDTO updateExerciseTextDTO) {
    exerciseTextService.updateExerciseText(updateExerciseTextDTO);
  }

  @DeleteMapping("/{exerciseTextId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteExerciseText(@PathVariable String exerciseTextId) {
    exerciseTextService.deleteExerciseText(exerciseTextId);
  }
}
