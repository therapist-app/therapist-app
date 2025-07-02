package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanMapper;
import jakarta.transaction.Transactional;
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
    return CounselingPlanMapper.INSTANCE.convertEntityToCounselingPlanOutputDTO(counselingPlan);
  }
}
