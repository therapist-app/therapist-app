package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseGoalRepository;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanPhaseRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateCounselingPlanPhaseGoalDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanPhaseGoalMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounselingPlanPhaseGoalService {

  private final CounselingPlanPhaseGoalRepository counselingPlanPhaseGoalRepository;
  private final CounselingPlanPhaseRepository counselingPlanPhaseRepository;

  public CounselingPlanPhaseGoalService(
      @Qualifier("counselingPlanPhaseGoalRepository")
          CounselingPlanPhaseGoalRepository counselingPlanPhaseGoalRepository,
      @Qualifier("counselingPlanPhaseRepository")
          CounselingPlanPhaseRepository counselingPlanPhaseRepository) {
    this.counselingPlanPhaseGoalRepository = counselingPlanPhaseGoalRepository;
    this.counselingPlanPhaseRepository = counselingPlanPhaseRepository;
  }

  public CounselingPlanPhaseGoalOutputDTO createCounselingPlanPhaseGoal(
      CreateCounselingPlanPhaseGoalDTO createCounselingPlanPhaseGoalDTO) {
    CounselingPlanPhase counselingPlanPhase =
        counselingPlanPhaseRepository.getReferenceById(
            createCounselingPlanPhaseGoalDTO.getCounselingPlanPhaseId());

    CounselingPlanPhaseGoal counselingPlanPhaseGoal = new CounselingPlanPhaseGoal();
    counselingPlanPhaseGoal.setCounselingPlanPhase(counselingPlanPhase);
    counselingPlanPhaseGoal.setGoalName(createCounselingPlanPhaseGoalDTO.getGoalName());
    counselingPlanPhaseGoal.setGoalDescription(
        createCounselingPlanPhaseGoalDTO.getGoalDescription());
    counselingPlanPhaseGoalRepository.save(counselingPlanPhaseGoal);
    counselingPlanPhaseGoalRepository.flush();
    return CounselingPlanPhaseGoalMapper.INSTANCE.convertEntityToCounselingPlanPhaseGoalOutputDTO(
        counselingPlanPhaseGoal);
  }

  public CounselingPlanPhaseGoalOutputDTO getCounselingPlanPhaseGoalById(String id) {
    CounselingPlanPhaseGoal counselingPlanPhaseGoal =
        counselingPlanPhaseGoalRepository.getReferenceById(id);
    return CounselingPlanPhaseGoalMapper.INSTANCE.convertEntityToCounselingPlanPhaseGoalOutputDTO(
        counselingPlanPhaseGoal);
  }

  public void deleteCounselingPlanPhaseGoal(String id) {
    counselingPlanPhaseGoalRepository.deleteById(id);
  }
}
