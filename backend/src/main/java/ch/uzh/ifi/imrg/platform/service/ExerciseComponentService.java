package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.repository.ExerciseComponentRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseComponentDTO;

import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ExerciseComponentService {

  private final ExerciseComponentRepository exerciseComponentRepository;
  private final ExerciseRepository exerciseRepository;

  public ExerciseComponentService(
      @Qualifier("exerciseComponentRepository") ExerciseComponentRepository exerciseComponentRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository) {
    this.exerciseComponentRepository = exerciseComponentRepository;
    this.exerciseRepository = exerciseRepository;
  }

  public void createExerciseComponent(
      CreateExerciseComponentDTO createExerciseComponentDTO, String therapistId) {
    Exercise exercise = exerciseRepository.getReferenceById(createExerciseComponentDTO.getExerciseId());
    SecurityUtil.checkOwnership(exercise, therapistId);

    ExerciseComponent exerciseComponent = new ExerciseComponent();
    exerciseComponent.setExercise(exercise);
    exerciseComponent.setExerciseComponentType(
        createExerciseComponentDTO.getExerciseComponentType());
    exerciseComponent.setOrderNumber(exercise.getExerciseComponents().size() + 1);
    exerciseComponent.setDescription(createExerciseComponentDTO.getDescription());

    exerciseComponentRepository.save(exerciseComponent);
  }

  public void createExerciseComponentWithFile(
      CreateExerciseComponentDTO createExerciseComponentDTO,
      MultipartFile file,
      String therapistId) {
    Exercise exercise = exerciseRepository.getReferenceById(createExerciseComponentDTO.getExerciseId());
    SecurityUtil.checkOwnership(exercise, therapistId);

    String extractedText = DocumentParserUtil.extractText(file);

    ExerciseComponent exerciseComponent = new ExerciseComponent();
    exerciseComponent.setExercise(exercise);
    exerciseComponent.setExerciseComponentType(
        createExerciseComponentDTO.getExerciseComponentType());
    exerciseComponent.setFileName(file.getOriginalFilename());
    exerciseComponent.setFileType(file.getContentType());
    exerciseComponent.setExtractedText(extractedText);
    exerciseComponent.setOrderNumber(exercise.getExerciseComponents().size() + 1);
    exerciseComponent.setDescription(createExerciseComponentDTO.getDescription());

    try {
      exerciseComponent.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file bytes", e);
    }

    exerciseComponentRepository.save(exerciseComponent);
  }

  public ExerciseComponent getExerciseComponent(String id, String therapistId) {
    ExerciseComponent exerciseComponent = exerciseComponentRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(exerciseComponent, therapistId);
    return exerciseComponent;
  }

  public ExerciseComponent downloadExerciseComponent(String id, String therapistId) {
    ExerciseComponent exerciseComponent = exerciseComponentRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(exerciseComponent, therapistId);
    exerciseComponent.getFileData();
    return exerciseComponent;
  }

  public void updateExerciseComponent(UpdateExerciseComponentDTO dto, String therapistId) {
    ExerciseComponent target = exerciseComponentRepository.getReferenceById(dto.getId());
    SecurityUtil.checkOwnership(target, therapistId);
    Exercise exercise = target.getExercise();
    SecurityUtil.checkOwnership(exercise, therapistId);

    // 1) Update the text if provided
    if (dto.getDescription() != null) {
      target.setDescription(dto.getDescription());
    }

    // 2) Only if they passed in an order number do we need to shuffle anything
    if (dto.getOrderNumber() != null) {
      int oldOrder = target.getOrderNumber();
      int newOrder = dto.getOrderNumber();

      // total existing slots = files + texts
      int totalSlots = exercise.getExerciseComponents().size();
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
          for (ExerciseComponent exerciseComponent : exercise.getExerciseComponents()) {
            int ord = exerciseComponent.getOrderNumber();
            if (ord >= newOrder && ord < oldOrder) {
              exerciseComponent.setOrderNumber(ord + 1);
              exerciseComponentRepository.save(exerciseComponent);
            }
          }
        } else {
          // moving down
          for (ExerciseComponent exerciseComponent : exercise.getExerciseComponents()) {
            int ord = exerciseComponent.getOrderNumber();
            if (ord <= newOrder && ord > oldOrder) {
              exerciseComponent.setOrderNumber(ord - 1);
              exerciseComponentRepository.save(exerciseComponent);
            }
          }
        }

        // 4) Finally, set the target to its new slot
        target.setOrderNumber(newOrder);
      }
    }

    // 5) Persist the target last so that its newOrder is final
    exerciseComponentRepository.save(target);
  }

  public void deleteExerciseComponent(String id, String therapistId) {
    ExerciseComponent exerciseComponent = exerciseComponentRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(exerciseComponent, therapistId);
    Exercise exercise = exerciseComponent.getExercise();

    for (ExerciseComponent exerciseComponent2 : exercise.getExerciseComponents()) {
      if (exerciseComponent2.getOrderNumber() > exerciseComponent.getOrderNumber()) {
        exerciseComponent2.setOrderNumber(exerciseComponent2.getOrderNumber() - 1);
        exerciseComponentRepository.save(exerciseComponent2);
      }
    }

    exerciseComponent.getExercise().getExerciseComponents().remove(exerciseComponent);
  }
}
