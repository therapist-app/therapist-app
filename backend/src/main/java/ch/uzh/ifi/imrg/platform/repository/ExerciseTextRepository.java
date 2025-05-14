package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.ExerciseText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("exerciseTextRepository")
public interface ExerciseTextRepository extends JpaRepository<ExerciseText, String> {}
