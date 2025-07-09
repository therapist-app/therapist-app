package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanMapper;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CounselingPlanService {

  private final PatientRepository patientRepository;

  public CounselingPlanService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public CounselingPlanOutputDTO getCounselingPlanByPatientId(String patientId) {
    CounselingPlan counselingPlan =
        patientRepository.getReferenceById(patientId).getCounselingPlan();
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
