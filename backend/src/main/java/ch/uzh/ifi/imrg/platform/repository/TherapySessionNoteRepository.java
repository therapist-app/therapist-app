package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.TherapySessionNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("therapySessionNoteRepository")
public interface TherapySessionNoteRepository extends JpaRepository<TherapySessionNote, String> {}
