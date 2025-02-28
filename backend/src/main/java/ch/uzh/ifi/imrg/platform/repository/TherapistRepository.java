package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("therapistRepository")
public interface TherapistRepository extends JpaRepository<Therapist, String> {

  boolean existsByEmail(String email);
}
