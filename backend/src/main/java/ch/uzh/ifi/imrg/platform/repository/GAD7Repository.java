package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.GAD7Test;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GAD7Repository extends JpaRepository<GAD7Test, String> {
  List<GAD7Test> findByPatient_Id(String patientId);
  List<GAD7Test> findByTherapySession_Id(String sessionId);
}
