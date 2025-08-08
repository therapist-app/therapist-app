package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateMeetingNoteDTOTest {

    @Test
    void settersAndGetters_work() {
        CreateMeetingNoteDTO dto = new CreateMeetingNoteDTO();
        dto.setMeetingId("m");
        dto.setTitle("t");
        dto.setContent("c");
        assertEquals("m", dto.getMeetingId());
        assertEquals("t", dto.getTitle());
        assertEquals("c", dto.getContent());
    }
}
