package ch.uzh.ifi.imrg.platform.repository;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("meetingRepository")
public interface MeetingRepository extends JpaRepository<Meeting, String> {
}
