package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdateMeetingNoteDTOTest {

    @Test
    void settersAndGetters_work() {
        UpdateMeetingNoteDTO dto = new UpdateMeetingNoteDTO();
        dto.setId("i");
        dto.setTitle("t");
        dto.setContent("c");
        assertEquals("i", dto.getId());
        assertEquals("t", dto.getTitle());
        assertEquals("c", dto.getContent());
    }
}
