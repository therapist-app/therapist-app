package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.CreatePatientDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.PatientDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientMapper;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Transactional
public class PatientService {

  private final Logger logger = LoggerFactory.getLogger(PatientService.class);

  private final PatientRepository patientRepository;
  private final TherapistRepository therapistRepository;

  private final PatientMapper mapper = PatientMapper.INSTANCE;

  @PersistenceContext private EntityManager entityManager;

  public PatientService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository,
      @Qualifier("patientDocumentRepository") PatientDocumentRepository patientDocumentRepository) {
    this.patientRepository = patientRepository;
    this.therapistRepository = therapistRepository;
  }

  public Patient registerPatient(String therapistId, CreatePatientDTO inputDTO) {
    Therapist therapist =
        therapistRepository
            .findById(therapistId)
            .orElseThrow(() -> new IllegalArgumentException("Therapist not found"));

    Patient patient = mapper.convertCreatePatientDtoToEntity(inputDTO);
    patient.setTherapist(therapist);

    patientRepository.save(patient);
    patientRepository.flush();
    entityManager.refresh(therapist);

    try {
      CreatePatientDTOPatientAPI createPatientDTOPatientAPI = new CreatePatientDTOPatientAPI();
      createPatientDTOPatientAPI.setEmail(inputDTO.getEmail());
      createPatientDTOPatientAPI.password("password");

      PatientAppAPIs.patientControllerPatientAPI
          .registerPatient(createPatientDTOPatientAPI)
          .block();
    } catch (WebClientResponseException e) {
      System.out.println(e.toString());
      logger.error(e.toString());
    } catch (Error e2) {
      System.out.println(e2.toString());
      logger.error(e2.toString());
    }

    return patient;
  }

  public Patient getPatientById(String patientId, Therapist loggedInTherapist) {
    Patient foundPatient =
        loggedInTherapist.getPatients().stream()
            .filter(p -> p.getId().equals(patientId))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    return foundPatient;
  }

  public List<Patient> getAllPatientsOfTherapist(Therapist loggedInTherapist) {

    return loggedInTherapist.getPatients();
  }

  public void deletePatient(String id) {
    Patient patient = patientRepository.getPatientById(id);
    patient.getTherapist().getPatients().remove(patient);
  }
}
