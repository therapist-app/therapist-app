package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.ExerciseComponentInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ExerciseComponentUpdateInputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.repository.ExerciseComponentRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
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
      @Qualifier("exerciseComponentRepository")
          ExerciseComponentRepository exerciseComponentRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository) {
    this.exerciseComponentRepository = exerciseComponentRepository;
    this.exerciseRepository = exerciseRepository;
  }

  public void createExerciseComponent(
      CreateExerciseComponentDTO createExerciseComponentDTO, String therapistId) {
    Exercise exercise =
        exerciseRepository.getReferenceById(createExerciseComponentDTO.getExerciseId());
    SecurityUtil.checkOwnership(exercise, therapistId);

    ExerciseComponent exerciseComponent = new ExerciseComponent();
    exerciseComponent.setExercise(exercise);
    exerciseComponent.setExerciseComponentType(
        createExerciseComponentDTO.getExerciseComponentType());
    exerciseComponent.setOrderNumber(exercise.getExerciseComponents().size() + 1);
    exerciseComponent.setExerciseComponentDescription(
        createExerciseComponentDTO.getExerciseComponentDescription());

    exerciseComponent = exerciseComponentRepository.save(exerciseComponent);

    ExerciseComponentInputDTOPatientAPI exerciseComponentInputDTOPatientAPI =
        new ExerciseComponentInputDTOPatientAPI()
            .id(exerciseComponent.getId())
            .exerciseComponentDescription(exerciseComponent.getExerciseComponentDescription())
            .orderNumber(exerciseComponent.getOrderNumber());

    PatientAppAPIs.coachExerciseControllerPatientAPI
        .createExerciseComponent(
            exercise.getPatient().getId(),
            createExerciseComponentDTO.getExerciseId(),
            exerciseComponentInputDTOPatientAPI)
        .block();
  }

  public void createExerciseComponentWithFile(
      CreateExerciseComponentDTO createExerciseComponentDTO,
      MultipartFile file,
      String therapistId) {
    Exercise exercise =
        exerciseRepository.getReferenceById(createExerciseComponentDTO.getExerciseId());
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
    exerciseComponent.setExerciseComponentDescription(
        createExerciseComponentDTO.getExerciseComponentDescription());

    try {
      exerciseComponent.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file bytes", e);
    }

    exerciseComponent = exerciseComponentRepository.save(exerciseComponent);

    ExerciseComponentInputDTOPatientAPI exerciseComponentInputDTOPatientAPI =
        new ExerciseComponentInputDTOPatientAPI()
            .id(exerciseComponent.getId())
            .exerciseComponentDescription(exerciseComponent.getExerciseComponentDescription())
            .exerciseComponentType(
                ExerciseComponentInputDTOPatientAPI.ExerciseComponentTypeEnum.fromValue(
                    exerciseComponent.getExerciseComponentType().toString()))
            .fileData(exerciseComponent.getFileData())
            .fileName(exerciseComponent.getFileName())
            .fileType(exerciseComponent.getFileType())
            .orderNumber(exerciseComponent.getOrderNumber());

    PatientAppAPIs.coachExerciseControllerPatientAPI
        .createExerciseComponent(
            exercise.getPatient().getId(),
            createExerciseComponentDTO.getExerciseId(),
            exerciseComponentInputDTOPatientAPI)
        .block();
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

    ExerciseComponentUpdateInputDTOPatientAPI exerciseComponentUpdateInputDTOPatientAPI =
        new ExerciseComponentUpdateInputDTOPatientAPI().id(dto.getId());

    if (dto.getExerciseComponentDescription() != null) {
      target.setExerciseComponentDescription(dto.getExerciseComponentDescription());
      exerciseComponentUpdateInputDTOPatientAPI.exerciseComponentDescription(
          dto.getExerciseComponentDescription());
    }

    if (dto.getOrderNumber() != null) {
      int oldOrder = target.getOrderNumber();
      int newOrder = dto.getOrderNumber();

      int totalSlots = exercise.getExerciseComponents().size();
      newOrder = Math.max(1, Math.min(newOrder, totalSlots));

      if (newOrder != oldOrder) {

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

        target.setOrderNumber(newOrder);
      }
    }

    exerciseComponentUpdateInputDTOPatientAPI.orderNumber(target.getOrderNumber());

    PatientAppAPIs.coachExerciseControllerPatientAPI
        .updateExerciseComponent(
            exercise.getPatient().getId(),
            exercise.getId(),
            target.getId(),
            exerciseComponentUpdateInputDTOPatientAPI)
        .block();

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

    PatientAppAPIs.coachExerciseControllerPatientAPI
        .deleteExerciseComponent(
            exerciseComponent.getExercise().getPatient().getId(),
            exerciseComponent.getExercise().getId(),
            id)
        .block();
  }
}
