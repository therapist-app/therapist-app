package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateMeetingDTOTest {

    @Test
    void settersAndGetters_work() {
        Instant start = Instant.now();
        Instant end   = start.plusSeconds(600);

        CreateMeetingDTO dto = new CreateMeetingDTO();
        dto.setMeetingStart(start);
        dto.setMeetingEnd(end);
        dto.setLocation("loc");
        dto.setPatientId("p1");

        assertEquals(start, dto.getMeetingStart());
        assertEquals(end, dto.getMeetingEnd());
        assertEquals("loc", dto.getLocation());
        assertEquals("p1", dto.getPatientId());
    }
}
