package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MeetingNoteOutputDTOTest {

    @Test
    void allArgsConstructor_setsAndGetsFields() {
        Instant now = Instant.now();

        MeetingNoteOutputDTO dto =
                new MeetingNoteOutputDTO("id1", now, now, "title", "content");

        assertEquals("id1", dto.getId());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals("title", dto.getTitle());
        assertEquals("content", dto.getContent());
    }
}
