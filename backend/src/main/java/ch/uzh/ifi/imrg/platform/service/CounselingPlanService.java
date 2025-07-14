package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanMapper;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CounselingPlanService {

  private final PatientRepository patientRepository;
  private final CounselingPlanRepository counselingPlanRepository;

  public CounselingPlanService(
      PatientRepository patientRepository, CounselingPlanRepository counselingPlanRepository) {
    this.patientRepository = patientRepository;
    this.counselingPlanRepository = counselingPlanRepository;
  }

  public CounselingPlanOutputDTO getCounselingPlanByPatientId(
      String patientId, String therapistId) {
    CounselingPlan counselingPlan =
        patientRepository.getReferenceById(patientId).getCounselingPlan();
    SecurityUtil.checkOwnership(counselingPlan, therapistId);
    return getOutputDto(counselingPlan);
  }

  public CounselingPlanOutputDTO updateCounselingPlan(
      UpdateCounselingPlanDTO updateCounselingPlanDTO, String therapistId) {
    CounselingPlan counselingPlan =
        counselingPlanRepository.getReferenceById(updateCounselingPlanDTO.getCounselingPlanId());
    SecurityUtil.checkOwnership(counselingPlan, therapistId);

    if (updateCounselingPlanDTO.getStartOfTherapy() != null) {
      counselingPlan.setStartOfTherapy(updateCounselingPlanDTO.getStartOfTherapy());
    }
    counselingPlanRepository.save(counselingPlan);
    return getOutputDto(counselingPlan);
  }

  private CounselingPlanOutputDTO getOutputDto(CounselingPlan counselingPlan) {
    CounselingPlanOutputDTO outputDTO =
        CounselingPlanMapper.INSTANCE.convertEntityToCounselingPlanOutputDTO(counselingPlan);
    List<CounselingPlanPhaseOutputDTO> mappedOutputDtos = new ArrayList<>();
    for (CounselingPlanPhase phase : counselingPlan.getCounselingPlanPhases()) {
      mappedOutputDtos.add(CounselingPlanPhaseService.getOutputDto(phase, counselingPlan));
    }
    outputDTO.setCounselingPlanPhasesOutputDTO(mappedOutputDtos);
    return outputDTO;
  }
}
