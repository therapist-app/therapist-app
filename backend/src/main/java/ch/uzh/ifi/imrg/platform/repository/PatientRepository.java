package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

  boolean existsById(String id);

  Patient getPatientById(String id);

  boolean existsByIdAndTherapistId(String patientId, String therapistId);
}
