package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.PsychologicalTestAssignmentInputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PsychologicalTestAssignmentOutputDTOPatientAPI;
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
          toAssignmentInput(patientId, psychologicalTestName, dto);
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

  public PsychologicalTestCreateDTO updatePsychologicalTestConfig(
      String patientId,
      String therapistId,
      PsychologicalTestCreateDTO dto,
      String psychologicalTestName) {

    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    try {
      PsychologicalTestAssignmentInputDTOPatientAPI input =
          toAssignmentInput(patientId, psychologicalTestName, dto);
      PatientAppAPIs.coachPsychologicalTestControllerPatientAPI
          .updatePsychologicalTestWithHttpInfo(patientId, psychologicalTestName, input)
          .block();

      return dto;

    } catch (Exception e) {
      logger.error(
          "Error assigning psychological test {} to patient {}", dto.getTestName(), patientId, e);
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to assign psychological test", e);
    }
  }

  public PsychologicalTestCreateDTO getPsychologicalTestConfig(
      String patientId, String therapistId, String psychologicalTestName) {

    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    try {
      PsychologicalTestAssignmentOutputDTOPatientAPI apiResult =
          PatientAppAPIs.coachPsychologicalTestControllerPatientAPI
              .getPsychologicalTestConfiguration(patientId, psychologicalTestName)
              .block();

      if (apiResult == null) {
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Psychological test configuration not found for patient " + patientId);
      }

      PsychologicalTestCreateDTO resultDto = new PsychologicalTestCreateDTO();
      resultDto.setPatientId(patientId);
      resultDto.setTestName(apiResult.getTestName());
      resultDto.setExerciseStart(apiResult.getExerciseStart());
      resultDto.setExerciseEnd(apiResult.getExerciseEnd());
      resultDto.setIsPaused(apiResult.getIsPaused());
      resultDto.setDoEveryNDays(apiResult.getDoEveryNDays());

      return resultDto;

    } catch (Exception e) {
      logger.error("Error getting psychological test configuration for patient {}", patientId, e);
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get psychological test configuration", e);
    }
  }

  private PsychologicalTestAssignmentInputDTOPatientAPI toAssignmentInput(
      String patientId, String testName, PsychologicalTestCreateDTO dto) {
    return new PsychologicalTestAssignmentInputDTOPatientAPI()
        .patientId(patientId)
        .testName(testName)
        .exerciseStart(dto.getExerciseStart())
        .exerciseEnd(dto.getExerciseEnd())
        .isPaused(dto.getIsPaused())
        .doEveryNDays(dto.getDoEveryNDays());
  }
}
