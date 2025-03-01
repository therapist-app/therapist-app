package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PatientService {

  private final PatientRepository patientRepository;
  private final TherapistRepository therapistRepository;
  private final PatientMapper mapper = PatientMapper.INSTANCE;

  public PatientService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      TherapistRepository therapistRepository) {
    this.patientRepository = patientRepository;
    this.therapistRepository = therapistRepository;
  }

  public PatientOutputDTO createPatient(CreatePatientDTO inputDTO) {
    Patient patient = mapper.convertCreatePatientDtoToEntity(inputDTO);
    Patient saved = patientRepository.save(patient);
    return mapper.convertEntityToPatientOutputDTO(saved);
  }

  public List<PatientOutputDTO> getAllPatients() {
    return patientRepository.findAll().stream()
        .map(mapper::convertEntityToPatientOutputDTO)
        .collect(Collectors.toList());
  }

  public PatientOutputDTO getPatientById(Long id) {
    Patient patient =
        patientRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
    return mapper.convertEntityToPatientOutputDTO(patient);
  }

  public PatientOutputDTO createPatientForTherapist(String therapistId, CreatePatientDTO inputDTO) {
    Therapist therapist =
        therapistRepository
            .findById(therapistId)
            .orElseThrow(() -> new IllegalArgumentException("Therapist not found"));

    Patient patient = mapper.convertCreatePatientDtoToEntity(inputDTO);
    patient.setTherapist(therapist);

    therapist.getPatients().add(patient);

    Patient saved = patientRepository.save(patient);
    therapistRepository.save(therapist);
    return mapper.convertEntityToPatientOutputDTO(saved);
  }

  @Transactional
  public List<PatientOutputDTO> getPatientsForTherapist(String therapistId) {
    Therapist therapist =
        therapistRepository
            .findById(therapistId)
            .orElseThrow(() -> new IllegalArgumentException("Therapist not found"));
    return therapist.getPatients().stream()
        .map(mapper::convertEntityToPatientOutputDTO)
        .collect(Collectors.toList());
  }
}
