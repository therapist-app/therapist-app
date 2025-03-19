package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("sessionRepository")
public interface TherapySessionRepository extends JpaRepository<TherapySession, String> {

}
