package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdateMeetingDTOTest {

    @Test
    void settersAndGetters_work() {
        Instant start = Instant.now();
        Instant end   = start.plusSeconds(1200);

        UpdateMeetingDTO dto = new UpdateMeetingDTO();
        dto.setId("u1");
        dto.setMeetingStart(start);
        dto.setMeetingEnd(end);
        dto.setLocation("loc");
        dto.setMeetingStatus(MeetingStatus.CANCELLED);

        assertEquals("u1", dto.getId());
        assertEquals(start, dto.getMeetingStart());
        assertEquals(end, dto.getMeetingEnd());
        assertEquals("loc", dto.getLocation());
        assertEquals(MeetingStatus.CANCELLED, dto.getMeetingStatus());
    }
}
