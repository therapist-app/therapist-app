package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.MeetingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("meetingNoteRepository")
public interface MeetingNoteRepository extends JpaRepository<MeetingNote, String> {
}
