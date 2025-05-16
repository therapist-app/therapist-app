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
        exercise.getExerciseFiles().size() + exercise.getExerciseTexts().size() + 1);
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

  public void updateExerciseText(UpdateExerciseFileDTO dto) {
    ExerciseFile target = exerciseFileRepository.getReferenceById(dto.getId());
    Exercise exercise = target.getExercise();

    // 1) Update the text if provided
    if (dto.getDescription() != null) {
      target.setDescription(dto.getDescription());
    }

    // 2) Only if they passed in an order number do we need to shuffle anything
    if (dto.getOrderNumber() != null) {
      int oldOrder = target.getOrderNumber();
      int newOrder = dto.getOrderNumber();

      // total existing slots = files + texts
      int totalSlots = exercise.getExerciseFiles().size() + exercise.getExerciseTexts().size();
      // clamp to [1..totalSlots]
      newOrder = Math.max(1, Math.min(newOrder, totalSlots));

      // if nothing to do, skip the heavy lifting
      if (newOrder != oldOrder) {

        // 3) For each **other** file/text, shift its order in the gap between old and
        // new
        // - if moving **up**: bump everything in [new, old-1] **up** by +1
        // - if moving **down**: push everything in [old+1, new] **down** by -1
        if (newOrder < oldOrder) {
          // moving up
          for (ExerciseFile file : exercise.getExerciseFiles()) {
            int ord = file.getOrderNumber();
            if (ord >= newOrder && ord < oldOrder) {
              file.setOrderNumber(ord + 1);
              exerciseFileRepository.save(file);
            }
          }
          for (ExerciseText text : exercise.getExerciseTexts()) {
            if (text.getId().equals(dto.getId())) continue;
            int ord = text.getOrderNumber();
            if (ord >= newOrder && ord < oldOrder) {
              text.setOrderNumber(ord + 1);
              exerciseTextRepository.save(text);
            }
          }
        } else {
          // moving down
          for (ExerciseFile file : exercise.getExerciseFiles()) {
            int ord = file.getOrderNumber();
            if (ord <= newOrder && ord > oldOrder) {
              file.setOrderNumber(ord - 1);
              exerciseFileRepository.save(file);
            }
          }
          for (ExerciseText text : exercise.getExerciseTexts()) {
            if (text.getId().equals(dto.getId())) continue;
            int ord = text.getOrderNumber();
            if (ord <= newOrder && ord > oldOrder) {
              text.setOrderNumber(ord - 1);
              exerciseTextRepository.save(text);
            }
          }
        }

        // 4) Finally, set the target to its new slot
        target.setOrderNumber(newOrder);
      }
    }

    // 5) Persist the target last so that its newOrder is final
    exerciseFileRepository.save(target);
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
