package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.ExerciseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("exerciseFileRepository")
public interface ExerciseFileRepository extends JpaRepository<ExerciseFile, String> {}
