package ch.uzh.ifi.imrg.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ch.uzh.ifi.imrg.platform.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsById(String id);
}
