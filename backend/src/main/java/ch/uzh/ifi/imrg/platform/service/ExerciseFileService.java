package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseFile;
import ch.uzh.ifi.imrg.platform.entity.ExerciseText;
import ch.uzh.ifi.imrg.platform.repository.ExerciseFileRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseTextRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseFileDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseFileDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ExerciseFileMapper;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import jakarta.transaction.Transactional;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ExerciseFileService {
  private final Logger logger = LoggerFactory.getLogger(ExerciseFileService.class);

  private final ExerciseFileRepository exerciseFileRepository;
  private final ExerciseTextRepository exerciseTextRepository;
  private final ExerciseRepository exerciseRepository;

  private final ExerciseFileMapper exerciseFileMapper = ExerciseFileMapper.INSTANCE;

  public ExerciseFileService(
      @Qualifier("exerciseFileRepository") ExerciseFileRepository exerciseFileRepository,
      @Qualifier("exerciseTextRepository") ExerciseTextRepository exerciseTextRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository) {
    this.exerciseFileRepository = exerciseFileRepository;
    this.exerciseRepository = exerciseRepository;
    this.exerciseTextRepository = exerciseTextRepository;
  }

  public void createExerciseFile(CreateExerciseFileDTO createExerciseFileDTO, MultipartFile file) {
    Exercise exercise = exerciseRepository.getReferenceById(createExerciseFileDTO.getExerciseId());

    String extractedText = DocumentParserUtil.extractText(file);

    ExerciseFile exerciseFile = new ExerciseFile();
    exerciseFile.setExercise(exercise);
    exerciseFile.setFileName(file.getOriginalFilename());
    exerciseFile.setFileType(file.getContentType());
    exerciseFile.setExtractedText(extractedText);
    exerciseFile.setOrderNumber(
        exercise.getExerciseFiles().size() + exercise.getExerciseTexts().size());
    exerciseFile.setDescription(createExerciseFileDTO.getDescription());

    try {
      exerciseFile.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file bytes", e);
    }

    exerciseFileRepository.save(exerciseFile);
  }

  public ExerciseFile getExerciseFile(String id) {
    ExerciseFile exerciseFile = exerciseFileRepository.getReferenceById(id);
    return exerciseFile;
  }

  public void updateExerciseFile(UpdateExerciseFileDTO updateExerciseFileDTO) {
    ExerciseFile exerciseFile =
        exerciseFileRepository.getReferenceById(updateExerciseFileDTO.getId());

    if (updateExerciseFileDTO.getDescription() != null) {
      exerciseFile.setDescription(updateExerciseFileDTO.getDescription());
    }

    if (updateExerciseFileDTO.getOrderNumber() != null) {
      Exercise exercise = exerciseFile.getExercise();
      Integer totalNumberOfFilesAndTexts =
          exercise.getExerciseFiles().size() + exercise.getExerciseTexts().size();
      if (updateExerciseFileDTO.getOrderNumber() >= totalNumberOfFilesAndTexts) {
        exerciseFile.setOrderNumber(totalNumberOfFilesAndTexts);
      } else {
        exerciseFile.setOrderNumber(updateExerciseFileDTO.getOrderNumber());
        for (ExerciseFile exerciseFile2 : exercise.getExerciseFiles()) {
          if (exerciseFile2.getOrderNumber() >= updateExerciseFileDTO.getOrderNumber()) {
            exerciseFile2.setOrderNumber(exerciseFile2.getOrderNumber() + 1);
            exerciseFileRepository.save(exerciseFile2);
          }
        }

        for (ExerciseText exerciseText2 : exercise.getExerciseTexts()) {
          if (exerciseText2.getOrderNumber() >= updateExerciseFileDTO.getOrderNumber()) {
            exerciseText2.setOrderNumber(exerciseText2.getOrderNumber() + 1);
            exerciseTextRepository.save(exerciseText2);
          }
        }
      }
    }

    exerciseFileRepository.save(exerciseFile);
  }

  public void deleteExcerciseFile(String id) {
    ExerciseFile exerciseFile = exerciseFileRepository.getReferenceById(id);
    Exercise exercise = exerciseFile.getExercise();

    for (ExerciseFile exerciseFile2 : exercise.getExerciseFiles()) {
      if (exerciseFile2.getOrderNumber() > exerciseFile.getOrderNumber()) {
        exerciseFile2.setOrderNumber(exerciseFile2.getOrderNumber() - 1);
        exerciseFileRepository.save(exerciseFile2);
      }
    }

    for (ExerciseText exerciseText2 : exercise.getExerciseTexts()) {
      if (exerciseText2.getOrderNumber() > exerciseFile.getOrderNumber()) {
        exerciseText2.setOrderNumber(exerciseText2.getOrderNumber() - 1);
        exerciseTextRepository.save(exerciseText2);
      }
    }

    exerciseFile.getExercise().getExerciseFiles().remove(exerciseFile);
  }
}
