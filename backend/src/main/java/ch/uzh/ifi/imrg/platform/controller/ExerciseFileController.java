package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.ExerciseFile;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseFileDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseFileDTO;
import ch.uzh.ifi.imrg.platform.service.ExerciseFileService;
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
@RequestMapping("/exercise-files")
public class ExerciseFileController {
  private final Logger logger = LoggerFactory.getLogger(ExerciseFileController.class);
  private final ExerciseFileService exerciseFileService;

  public ExerciseFileController(ExerciseFileService exerciseFileService) {
    this.exerciseFileService = exerciseFileService;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createExerciseFile(
      @RequestPart("metadata") CreateExerciseFileDTO createExerciseFileDTO,
      @RequestPart("file") MultipartFile file,
      HttpServletRequest request) {
    exerciseFileService.createExerciseFile(createExerciseFileDTO, file);
  }

  @GetMapping("/{exerciseFileId}/download")
  public ResponseEntity<Resource> downloadExerciseFile(@PathVariable String exerciseFileId) {
    ExerciseFile exerciseFile = exerciseFileService.getExerciseFile(exerciseFileId);
    ByteArrayResource resource = new ByteArrayResource(exerciseFile.getFileData());

    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + exerciseFile.getFileName() + "\"")
        .contentType(MediaType.parseMediaType(exerciseFile.getFileType()))
        .contentLength(exerciseFile.getFileData().length)
        .body(resource);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public void updateExerciseFile(@RequestBody UpdateExerciseFileDTO updateExerciseFileDTO) {
    exerciseFileService.updateExerciseFile(updateExerciseFileDTO);
  }

  @DeleteMapping("/{exerciseFileId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteExcerciseFile(@PathVariable String exerciseFileId) {
    exerciseFileService.deleteExcerciseFile(exerciseFileId);
  }
}
