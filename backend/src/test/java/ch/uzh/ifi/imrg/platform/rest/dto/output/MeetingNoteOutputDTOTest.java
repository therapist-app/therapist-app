package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class MeetingNoteOutputDTOTest {

    @Test
    void constructorGettersEqualsHashCodeToString() {
        Instant c = Instant.now();
        Instant u = c.plusSeconds(10);

        MeetingNoteOutputDTO a = new MeetingNoteOutputDTO("id1", c, u, "t", "content");
        MeetingNoteOutputDTO b = new MeetingNoteOutputDTO("id1", c, u, "t", "content");

        assertEquals("id1", a.getId());
        assertEquals(c, a.getCreatedAt());
        assertEquals(u, a.getUpdatedAt());
        assertEquals("t", a.getTitle());
        assertEquals("content", a.getContent());

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertTrue(a.toString().contains("content"));

        MeetingNoteOutputDTO c2 = new MeetingNoteOutputDTO("id1", c, u, "t2", "content");
        assertNotEquals(a, c2);
    }
}
