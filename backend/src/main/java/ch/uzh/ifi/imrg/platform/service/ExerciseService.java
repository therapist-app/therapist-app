package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapySessionRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExcerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ExerciseMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExerciseService {
  private final Logger logger = LoggerFactory.getLogger(PatientService.class);

  private final ExerciseRepository exerciseRepository;
  private final TherapySessionRepository therapySessionRepository;

  private final ExerciseMapper exerciseMapper = ExerciseMapper.INSTANCE;

  public ExerciseService(
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository,
      @Qualifier("therapySessionRepository") TherapySessionRepository therapySessionRepository) {
    this.exerciseRepository = exerciseRepository;
    this.therapySessionRepository = therapySessionRepository;
  }

  public ExerciseOutputDTO createExercise(CreateExcerciseDTO createExcerciseDTO) {

    TherapySession therapySession = therapySessionRepository.getReferenceById(createExcerciseDTO.getTherapySessionId());

    Exercise exercise = new Exercise();
    exercise.setTherapySession(therapySession);
    exercise.setTitle(createExcerciseDTO.getTitle());
    exercise.setExerciseType(createExcerciseDTO.getExerciseType());

    Exercise savedExercise = exerciseRepository.save(exercise);

    return exerciseMapper.convertEntityToExerciseOutputDTO(savedExercise);
  }

  public ExerciseOutputDTO getExerciseById(String id) {
    Exercise exercise = exerciseRepository.getReferenceById(id);
    return exerciseMapper.convertEntityToExerciseOutputDTO(exercise);
  }

  public List<ExerciseOutputDTO> getAllExercisesOfTherapySession(String therapySessionId) {
    TherapySession therapySession = therapySessionRepository.getReferenceById(therapySessionId);
    return therapySession.getExercises().stream()
        .map(exerciseMapper::convertEntityToExerciseOutputDTO)
        .collect(Collectors.toList());
  }

  public ExerciseOutputDTO updateExercise(UpdateExerciseDTO updateExerciseDTO) {
    Exercise exercise = exerciseRepository.getReferenceById(updateExerciseDTO.getId());
    if (updateExerciseDTO.getTitle() != null) {
      exercise.setTitle(updateExerciseDTO.getTitle());
    }
    if (updateExerciseDTO.getExerciseType() != null) {
      exercise.setExerciseType(updateExerciseDTO.getExerciseType());
    }

    Exercise updatedExcercise = exerciseRepository.save(exercise);
    return exerciseMapper.convertEntityToExerciseOutputDTO(updatedExcercise);
  }

  public void deleteExcercise(String id) {
    Exercise exercise = exerciseRepository.getReferenceById(id);
    exercise.getTherapySession().getExercises().remove(exercise);
  }
}
