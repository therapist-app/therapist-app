package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.AddExerciseToCounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.RemoveExerciseFromCounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounselingPlanPhaseService {

  private final CounselingPlanPhaseRepository counselingPlanPhaseRepository;
  private final ExerciseRepository exerciseRepository;

  public CounselingPlanPhaseService(
      @Qualifier("counselingPlanPhaseRepository")
          CounselingPlanPhaseRepository counselingPlanPhaseRepository,
      @Qualifier("exerciseRepository") ExerciseRepository exerciseRepository) {
    this.counselingPlanPhaseRepository = counselingPlanPhaseRepository;
    this.exerciseRepository = exerciseRepository;
  }

  public CounselingPlanPhaseOutputDTO createCounselingPlanPhase(
      CreateCounselingPlanPhaseDTO createCounselingPlanPhaseDTO) {
    CounselingPlanPhase counselingPlanPhase = new CounselingPlanPhase();
    counselingPlanPhase.setPhaseName(createCounselingPlanPhaseDTO.getPhaseName());
    counselingPlanPhase.setStartDate(createCounselingPlanPhaseDTO.getStartDate());
    counselingPlanPhase.setEndDate(createCounselingPlanPhaseDTO.getEndDate());
    counselingPlanPhaseRepository.save(counselingPlanPhase);
    counselingPlanPhaseRepository.flush();
    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public CounselingPlanPhaseOutputDTO addExerciseToCounselingPlanPhase(
      AddExerciseToCounselingPlanPhase addExerciseToCounselingPlanPhaseDTO) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            addExerciseToCounselingPlanPhaseDTO.getCounselingPlanPhaseId());
    Exercise exercise =
        exerciseRepository.getReferenceById(addExerciseToCounselingPlanPhaseDTO.getExerciseId());
    counselingPlanPhase.getPhaseExercises().add(exercise);
    counselingPlanPhaseRepository.save(counselingPlanPhase);
    counselingPlanPhaseRepository.flush();
    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public CounselingPlanPhaseOutputDTO removeExerciseFromCounselingPlanPhase(
      RemoveExerciseFromCounselingPlanPhase removeExerciseFromCounselingPlanPhaseDTO) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            removeExerciseFromCounselingPlanPhaseDTO.getCounselingPlanPhaseId());
    Exercise exercise =
        exerciseRepository.getReferenceById(
            removeExerciseFromCounselingPlanPhaseDTO.getExerciseId());
    counselingPlanPhase.getPhaseExercises().remove(exercise);
    counselingPlanPhaseRepository.save(counselingPlanPhase);
    counselingPlanPhaseRepository.flush();
    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public CounselingPlanPhaseOutputDTO getCounselingPlanPhaseById(String id) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository
            .findById(id)
            .orElseThrow(() -> new Error("Counseling plan phase not found with id: " + id));
    return CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(
        counselingPlanPhase);
  }

  public void deleteCounselingPlanPhase(String id) {
    counselingPlanPhaseRepository.deleteById(id);
  }
}
