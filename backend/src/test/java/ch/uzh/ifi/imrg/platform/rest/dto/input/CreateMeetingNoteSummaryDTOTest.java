package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateMeetingNoteSummaryDTOTest {

    @Test
    void gettersSettersToStringEqualsHashCode() {
        CreateMeetingNoteSummaryDTO dto1 = new CreateMeetingNoteSummaryDTO();
        dto1.setMeetingId("m1");
        dto1.setLanguage(Language.German);
        assertEquals("m1", dto1.getMeetingId());
        assertEquals(Language.German, dto1.getLanguage());
        String s = dto1.toString();
        assertTrue(s.contains("meetingId"));
        assertTrue(s.contains("language"));

        CreateMeetingNoteSummaryDTO dto2 = new CreateMeetingNoteSummaryDTO();
        dto2.setMeetingId("m1");
        dto2.setLanguage(Language.German);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setMeetingId("m2");
        assertNotEquals(dto1, dto2);
    }
}
