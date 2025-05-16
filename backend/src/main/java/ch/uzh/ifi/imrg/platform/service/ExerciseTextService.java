package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseFile;
import ch.uzh.ifi.imrg.platform.entity.ExerciseText;
import ch.uzh.ifi.imrg.platform.repository.ExerciseFileRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseTextRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseTextDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseTextDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ExerciseTextMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExerciseTextService {

  private final Logger logger = LoggerFactory.getLogger(ExerciseTextService.class);

  private final ExerciseTextRepository exerciseTextRepository;
  private final ExerciseFileRepository exerciseFileRepository;
  private final ExerciseRepository exerciseRepository;

  private final ExerciseTextMapper exerciseTextMapper = ExerciseTextMapper.INSTANCE;

  public ExerciseTextService(
      @Qualifier("exerciseTextRepository") ExerciseTextRepository exerciseTextRepository,
      @Qualifier("exerciseFileRepository") ExerciseFileRepository exerciseFileRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository) {

    this.exerciseTextRepository = exerciseTextRepository;
    this.exerciseFileRepository = exerciseFileRepository;
    this.exerciseRepository = exerciseRepository;
  }

  public void createExerciseText(CreateExerciseTextDTO createExerciseTextDTO) {
    Exercise exercise = exerciseRepository.getReferenceById(createExerciseTextDTO.getExerciseId());

    ExerciseText exerciseText = new ExerciseText();
    exerciseText.setExercise(exercise);
    exerciseText.setOrderNumber(
        exercise.getExerciseFiles().size() + exercise.getExerciseTexts().size() + 1);
    exerciseText.setText(createExerciseTextDTO.getText());

    exerciseTextRepository.save(exerciseText);
  }

  public void updateExerciseText(UpdateExerciseTextDTO updateExerciseTextDTO) {
    ExerciseText exerciseText =
        exerciseTextRepository.getReferenceById(updateExerciseTextDTO.getId());

    if (updateExerciseTextDTO.getText() != null) {
      exerciseText.setText(updateExerciseTextDTO.getText());
    }

    if (updateExerciseTextDTO.getOrderNumber() != null) {
      Exercise exercise = exerciseText.getExercise();
      Integer totalNumberOfFilesAndTexts =
          exercise.getExerciseFiles().size() + exercise.getExerciseTexts().size();
      if (updateExerciseTextDTO.getOrderNumber() > totalNumberOfFilesAndTexts) {
        exerciseText.setOrderNumber(totalNumberOfFilesAndTexts + 1);
      } else {
        exerciseText.setOrderNumber(updateExerciseTextDTO.getOrderNumber());
        for (ExerciseFile exerciseFile2 : exercise.getExerciseFiles()) {
          if (exerciseFile2.getOrderNumber() >= updateExerciseTextDTO.getOrderNumber()) {
            exerciseFile2.setOrderNumber(exerciseFile2.getOrderNumber() + 1);
            exerciseFileRepository.save(exerciseFile2);
          }
        }

        for (ExerciseText exerciseText2 : exercise.getExerciseTexts()) {
          if (exerciseText2.getOrderNumber() >= updateExerciseTextDTO.getOrderNumber()
              && exerciseText2.getId() != exerciseText.getId()) {
            exerciseText2.setOrderNumber(exerciseText2.getOrderNumber() + 1);
            exerciseTextRepository.save(exerciseText2);
          }
        }
      }
    }
    exerciseTextRepository.save(exerciseText);
  }

  public void deleteExerciseText(String id) {
    ExerciseText exerciseText = exerciseTextRepository.getReferenceById(id);
    Exercise exercise = exerciseText.getExercise();

    for (ExerciseFile exerciseFile2 : exercise.getExerciseFiles()) {
      if (exerciseFile2.getOrderNumber() > exerciseText.getOrderNumber()) {
        exerciseFile2.setOrderNumber(exerciseFile2.getOrderNumber() - 1);
        exerciseFileRepository.save(exerciseFile2);
      }
    }

    for (ExerciseText exerciseText2 : exercise.getExerciseTexts()) {
      if (exerciseText2.getOrderNumber() > exerciseText.getOrderNumber()) {
        exerciseText2.setOrderNumber(exerciseText2.getOrderNumber() - 1);
        exerciseTextRepository.save(exerciseText2);
      }
    }

    exerciseText.getExercise().getExerciseTexts().remove(exerciseText);
  }
}
