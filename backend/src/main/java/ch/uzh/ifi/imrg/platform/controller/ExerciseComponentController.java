package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.service.ExerciseComponentService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/exercise-components")
public class ExerciseComponentController {
  private final Logger logger = LoggerFactory.getLogger(ExerciseComponentController.class);
  private final ExerciseComponentService exerciseComponentService;

  public ExerciseComponentController(ExerciseComponentService exerciseComponentService) {
    this.exerciseComponentService = exerciseComponentService;
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public void createExerciseComponent(
      @RequestBody CreateExerciseComponentDTO createExerciseComponentDTO) {
    exerciseComponentService.createExerciseComponent(createExerciseComponentDTO);
  }

  @PostMapping(path = "/with-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createExerciseComponentWithFile(
      @RequestPart("metadata") CreateExerciseComponentDTO createExerciseComponentDTO,
      @RequestPart("file") MultipartFile file,
      HttpServletRequest request) {
    exerciseComponentService.createExerciseComponentWithFile(createExerciseComponentDTO, file);
  }

  @GetMapping("/{exerciseComponentId}/download")
  public ResponseEntity<Resource> downloadExerciseComponentFile(
      @PathVariable String exerciseComponentId) {
    ExerciseComponent exerciseComponent =
        exerciseComponentService.downloadExerciseComponent(exerciseComponentId);
    ByteArrayResource resource = new ByteArrayResource(exerciseComponent.getFileData());

    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + exerciseComponent.getFileName() + "\"")
        .contentType(MediaType.parseMediaType(exerciseComponent.getFileType()))
        .contentLength(exerciseComponent.getFileData().length)
        .body(resource);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public void updateExerciseComponent(
      @RequestBody UpdateExerciseComponentDTO updateExerciseComponentDTO) {
    exerciseComponentService.updateExerciseComponent(updateExerciseComponentDTO);
  }

  @DeleteMapping("/{exerciseComponentId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteExerciseComponent(@PathVariable String exerciseComponentId) {
    exerciseComponentService.deleteExerciseComponent(exerciseComponentId);
  }
}
