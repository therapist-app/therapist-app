package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.CreatePatientDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientMapper;
import ch.uzh.ifi.imrg.platform.utils.PasswordGenerationUtil;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class PatientService {

  private final Logger logger = LoggerFactory.getLogger(PatientService.class);

  private final PatientRepository patientRepository;
  private final TherapistRepository therapistRepository;
  private final CounselingPlanRepository counselingPlanRepository;

  private final PatientMapper mapper = PatientMapper.INSTANCE;

  @PersistenceContext
  private EntityManager entityManager;

  public PatientService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository,
      @Qualifier("patientDocumentRepository") PatientDocumentRepository patientDocumentRepository,
      @Qualifier("counselingPlanRepository") CounselingPlanRepository counselingPlanRepository) {
    this.patientRepository = patientRepository;
    this.therapistRepository = therapistRepository;
    this.counselingPlanRepository = counselingPlanRepository;
  }

  public PatientOutputDTO registerPatient(CreatePatientDTO inputDTO, String therapistId) {
    Therapist therapist = therapistRepository
        .findById(therapistId)
        .orElseThrow(() -> new IllegalArgumentException("Therapist not found"));

    Patient patient = mapper.convertCreatePatientDtoToEntity(inputDTO);
    patient.setTherapist(therapist);

    String initialPassword = PasswordGenerationUtil.generateUserFriendlyPassword();

    patient.setInitialPassword(initialPassword);

    CounselingPlan counselingPlan = new CounselingPlan();
    counselingPlan.setStartOfTherapy(Instant.now());
    counselingPlanRepository.save(counselingPlan);
    patient.setCounselingPlan(counselingPlan);

    Patient createdPatient = patientRepository.save(patient);
    patientRepository.flush();

    CreatePatientDTOPatientAPI createPatientDTOPatientAPI = new CreatePatientDTOPatientAPI()
        .id(createdPatient.getId())
        .email(createdPatient.getEmail())
        .password(createdPatient.getInitialPassword())
        .coachAccessKey(PatientAppAPIs.COACH_ACCESS_KEY);

    PatientAppAPIs.coachPatientControllerPatientAPI
        .registerPatient1(createPatientDTOPatientAPI)
        .block();

    return mapper.convertEntityToPatientOutputDTO(createdPatient);
  }

  public PatientOutputDTO getPatientById(String patientId, String therapistId) {
    Patient foundPatient = patientRepository.getPatientById(patientId);
    SecurityUtil.checkOwnership(foundPatient, therapistId);
    return mapper.convertEntityToPatientOutputDTO(foundPatient);
  }

  public List<PatientOutputDTO> getAllPatientsOfTherapist(String therapistId) {
    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    return therapist.getPatients().stream()
        .map(PatientMapper.INSTANCE::convertEntityToPatientOutputDTO)
        .collect(Collectors.toList());
  }

  public void deletePatient(String id, String therapistId) {
    Patient patient = patientRepository.getPatientById(id);
    SecurityUtil.checkOwnership(patient, therapistId);
    patient.getTherapist().getPatients().remove(patient);
  }
}
