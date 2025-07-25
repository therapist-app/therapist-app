package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.LogOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.LogOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CoachLogMapper;
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
public class PatientLogService {
  private final PatientRepository patientRepository;
  private static final Logger logger = LoggerFactory.getLogger(PatientLogService.class);

  public PatientLogService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public List<LogOutputDTO> listAll(String patientId, String logType, String therapistId) {
    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    try {
      List<LogOutputDTOPatientAPI> apiResults =
          PatientAppAPIs.coachLogControllerPatientAPI
              .listAll1(patientId, logType)
              .collectList()
              .block();

      if (apiResults == null) {
        logger.warn("No logs found for patient {} and type {}", patientId, logType);
        return Collections.emptyList();
      }

      return apiResults.stream()
          .map(
              apiDto -> {
                try {
                  return CoachLogMapper.INSTANCE.apiToLocal(apiDto);
                } catch (Exception e) {
                  logger.error(
                      "Error mapping log for patient {} and type {}", patientId, logType, e);
                  return null;
                }
              })
          .filter(Objects::nonNull)
          .toList();
    } catch (Exception e) {
      logger.error("Error fetching logs for patient {} and type {}", patientId, logType, e);
      throw new RuntimeException("Failed to retrieve logs", e);
    }
  }
}
