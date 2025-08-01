package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.PsychologicalTestAssignmentInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PsychologicalTestOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestCreateDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class PatientTestService {
  private final PatientRepository patientRepository;
  private static final Logger logger = LoggerFactory.getLogger(PatientTestService.class);

  public PatientTestService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public List<PsychologicalTestOutputDTO> getTestsByPatient(
      String patientId, String therapistId, String psychologicalTestName) {
    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    try {
      List<PsychologicalTestOutputDTOPatientAPI> apiResults =
          PatientAppAPIs.coachPsychologicalTestControllerPatientAPI
              .getPsychologicalTestResults1(patientId, psychologicalTestName)
              .collectList()
              .block();

      if (apiResults == null) {
        logger.warn("No test results found for patient {}", patientId);
        return Collections.emptyList();
      }

      return apiResults.stream()
          .map(
              apiDto -> {
                try {
                  return PatientPsychologicalTestMapper.INSTANCE.toPsychologicalTestOutputDTO(
                      apiDto);
                } catch (Exception e) {
                  logger.error("Error mapping test result for patient {}", patientId, e);
                  return null;
                }
              })
          .filter(Objects::nonNull)
          .toList();
    } catch (Exception e) {
      logger.error("Error fetching test results for patient {}", patientId, e);
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve test results", e);
    }
  }

  public PsychologicalTestCreateDTO assignPsychologicalTest(
      String patientId,
      String therapistId,
      PsychologicalTestCreateDTO dto,
      String psychologicalTestName) {

    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    try {
      PsychologicalTestAssignmentInputDTOPatientAPI input =
          new PsychologicalTestAssignmentInputDTOPatientAPI()
              .patientId(patientId)
              .testName(psychologicalTestName)
              .exerciseStart(dto.getExerciseStart())
              .exerciseEnd(dto.getExerciseEnd())
              .isPaused(dto.getIsPaused())
              .doEveryNDays(dto.getDoEveryNDays());

      PatientAppAPIs.coachPsychologicalTestControllerPatientAPI
          .createPsychologicalTest1(patientId, psychologicalTestName, input)
          .block();

      return dto;

    } catch (Exception e) {
      logger.error(
          "Error assigning psychological test {} to patient {}", dto.getTestName(), patientId, e);
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to assign psychological test", e);
    }
  }
}
