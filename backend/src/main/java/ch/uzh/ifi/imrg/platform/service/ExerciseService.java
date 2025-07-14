package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ExerciseMapper;
import ch.uzh.ifi.imrg.platform.utils.DateUtil;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
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
  private final PatientRepository patientRepository;

  private final ExerciseMapper exerciseMapper = ExerciseMapper.INSTANCE;

  public ExerciseService(
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository,
      @Qualifier("patientRepository") PatientRepository patientRepository) {
    this.exerciseRepository = exerciseRepository;
    this.patientRepository = patientRepository;
  }

  public ExerciseOutputDTO createExercise(CreateExerciseDTO createExerciseDTO, String therapistId) {

    Patient patient = patientRepository.getReferenceById(createExerciseDTO.getPatientId());
    SecurityUtil.checkOwnership(patient, therapistId);

    Exercise exercise = new Exercise();
    exercise.setPatient(patient);
    exercise.setTitle(createExerciseDTO.getTitle());
    exercise.setExerciseType(createExerciseDTO.getExerciseType());
    exercise.setExerciseStart(createExerciseDTO.getExerciseStart());
    exercise.setExerciseEnd(
        DateUtil.addAmountOfWeeks(
            createExerciseDTO.getExerciseStart(), createExerciseDTO.getDurationInWeeks()));
    exercise.setIsPaused(false);

    Exercise savedExercise = exerciseRepository.save(exercise);

    return exerciseMapper.convertEntityToExerciseOutputDTO(savedExercise);
  }

  public ExerciseOutputDTO getExerciseById(String id, String therapistId) {
    Exercise exercise = exerciseRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(exercise, therapistId);
    return exerciseMapper.convertEntityToExerciseOutputDTO(exercise);
  }

  public List<ExerciseOutputDTO> getAllExercisesOfPatient(String patientId, String therapistId) {
    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    List<Exercise> exercises = patient.getExercises();

    return exercises.stream()
        .map(exerciseMapper::convertEntityToExerciseOutputDTO)
        .collect(Collectors.toList());
  }

  public ExerciseOutputDTO updateExercise(UpdateExerciseDTO updateExerciseDTO, String therapistId) {
    Exercise exercise = exerciseRepository.getReferenceById(updateExerciseDTO.getId());
    SecurityUtil.checkOwnership(exercise, therapistId);

    if (updateExerciseDTO.getTitle() != null) {
      exercise.setTitle(updateExerciseDTO.getTitle());
    }
    if (updateExerciseDTO.getExerciseType() != null) {
      exercise.setExerciseType(updateExerciseDTO.getExerciseType());
    }

    if (updateExerciseDTO.getExerciseStart() != null) {
      exercise.setExerciseStart(updateExerciseDTO.getExerciseStart());
    }

    if (updateExerciseDTO.getExerciseEnd() != null) {
      exercise.setExerciseEnd(updateExerciseDTO.getExerciseEnd());
    }

    if (updateExerciseDTO.getIsPaused() != null) {
      exercise.setIsPaused(updateExerciseDTO.getIsPaused());
    }

    Exercise updatedExercise = exerciseRepository.save(exercise);
    return exerciseMapper.convertEntityToExerciseOutputDTO(updatedExercise);
  }

  public void deleteExercise(String id, String therapistId) {
    Exercise exercise = exerciseRepository.getReferenceById(id);
    SecurityUtil.checkOwnership(exercise, therapistId);
    exercise.getPatient().getExercises().remove(exercise);
  }
}
