package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MeetingOutputDTOTest {

    @Test
    void settersAndGetters_work() {
        Instant now = Instant.now();

        MeetingOutputDTO dto = new MeetingOutputDTO();
        dto.setId("m1");
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        dto.setMeetingStart(now);
        dto.setMeetingEnd(now.plusSeconds(3600));
        dto.setLocation("room");
        dto.setMeetingStatus(MeetingStatus.CONFIRMED);

        MeetingNoteOutputDTO note = new MeetingNoteOutputDTO("n", now, now, "t", "c");
        dto.setMeetingNotesOutputDTO(List.of(note));

        assertEquals("m1", dto.getId());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(now, dto.getMeetingStart());
        assertEquals(now.plusSeconds(3600), dto.getMeetingEnd());
        assertEquals("room", dto.getLocation());
        assertEquals(MeetingStatus.CONFIRMED, dto.getMeetingStatus());
        assertEquals(1, dto.getMeetingNotesOutputDTO().size());
        assertSame(note, dto.getMeetingNotesOutputDTO().getFirst());
    }
}
