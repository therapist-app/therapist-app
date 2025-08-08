package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.MeetingNote;
import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MeetingsMapperTest {

    @Test
    void mapMeetingNotes_nullInput_returnsNull() {
        assertNull(MeetingsMapper.INSTANCE.mapMeetingNotes(null));
    }

    @Test
    void fullEntity_mapsToDto() {
        MeetingNote n1 = new MeetingNote();
        n1.setContent("n1");
        MeetingNote n2 = new MeetingNote();
        n2.setContent("n2");

        Meeting meeting = new Meeting();
        meeting.setMeetingStart(Instant.parse("2025-08-08T08:00:00Z"));
        meeting.setMeetingEnd(Instant.parse("2025-08-08T09:00:00Z"));
        meeting.setLocation("room");
        meeting.setMeetingStatus(MeetingStatus.CONFIRMED);
        meeting.getMeetingNotes().addAll(List.of(n1, n2));

        MeetingOutputDTO dto =
                MeetingsMapper.INSTANCE.convertEntityToMeetingOutputDTO(meeting);

        assertEquals(meeting.getId(), dto.getId());
        assertEquals("room", dto.getLocation());
        assertEquals(MeetingStatus.CONFIRMED, dto.getMeetingStatus());
        assertEquals(2, dto.getMeetingNotesOutputDTO().size());

        List<String> contents =
                dto.getMeetingNotesOutputDTO().stream()
                        .map(d -> d.getContent())
                        .collect(Collectors.toList());

        assertTrue(contents.containsAll(List.of("n1", "n2")));
    }
}
