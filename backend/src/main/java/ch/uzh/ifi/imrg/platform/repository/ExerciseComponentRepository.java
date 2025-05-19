package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("exerciseComponentRepository")
public interface ExerciseComponentRepository extends JpaRepository<ExerciseComponent, String> {
}
