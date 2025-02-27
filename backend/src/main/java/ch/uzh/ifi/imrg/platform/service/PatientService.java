package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;
    private final TherapistRepository therapistRepository;

    public PatientService(@Qualifier("patientRepository") PatientRepository patientRepository,
            TherapistRepository therapistRepository) {
        this.patientRepository = patientRepository;
        this.therapistRepository = therapistRepository;
    }

    public PatientOutputDTO createPatient(CreatePatientDTO inputDTO) {
        Patient patient = new Patient();
        patient.setName(inputDTO.getName());

        Patient saved = patientRepository.save(patient);
        return entityToOutputDTO(saved);
    }

    public List<PatientOutputDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::entityToOutputDTO)
                .collect(Collectors.toList());
    }

    public PatientOutputDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        return entityToOutputDTO(patient);
    }

    private PatientOutputDTO entityToOutputDTO(Patient patient) {
        return new PatientOutputDTO(patient.getId(), patient.getName());
    }

    public PatientOutputDTO createPatientForTherapist(String therapistId, CreatePatientDTO inputDTO) {
        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new IllegalArgumentException("Therapist not found"));

        Patient patient = new Patient();
        patient.setName(inputDTO.getName());

        patient.setTherapist(therapist);

        therapist.getPatients().add(patient);

        Patient saved = patientRepository.save(patient);
        therapistRepository.save(therapist);
        return new PatientOutputDTO(saved.getId(), saved.getName());
    }

    @Transactional
    public List<PatientOutputDTO> getPatientsForTherapist(String therapistId) {
        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new IllegalArgumentException("Therapist not found"));
        return therapist.getPatients().stream()
                .map(patient -> new PatientOutputDTO(patient.getId(), patient.getName()))
                .collect(Collectors.toList());
    }
}
