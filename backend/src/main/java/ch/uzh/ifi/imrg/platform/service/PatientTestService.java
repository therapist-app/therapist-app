package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.PsychologicalTestOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientPsychologicalTestMapper;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PatientTestService {
  private final PatientRepository patientRepository;
  private static final String GAD7_TEST_NAME = "GAD7";
  private static final Logger logger = LoggerFactory.getLogger(PatientTestService.class);

  public PatientTestService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public List<PsychologicalTestOutputDTO> getTestsByPatient(String patientId, String therapistId) {
    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    try {
      List<PsychologicalTestOutputDTOPatientAPI> apiResults = PatientAppAPIs.coachPsychologicalTestControllerPatientAPI
              .getPsychologicalTestResults1(patientId, GAD7_TEST_NAME)
              .collectList()
              .block();

      if (apiResults == null) {
        logger.warn("No test results found for patient {}", patientId);
        return Collections.emptyList();
      }

      return apiResults.stream()
              .map(apiDto -> {
                try {
                  return PatientPsychologicalTestMapper.INSTANCE.toPsychologicalTestOutputDTO(apiDto);
                } catch (Exception e) {
                  logger.error("Error mapping test result for patient {}", patientId, e);
                  return null;
                }
              })
              .filter(Objects::nonNull)
              .toList();
    } catch (Exception e) {
      logger.error("Error fetching test results for patient {}", patientId, e);
      throw new RuntimeException("Failed to retrieve test results", e);
    }
  }
}