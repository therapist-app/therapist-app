package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Meeting;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientMapperTest {

    PatientMapper mapper = PatientMapper.INSTANCE;

    @Test
    void mapMeetings_nullAndNonNull() {
        assertNull(mapper.mapMeetings(null));
        Meeting m = new Meeting();
        List<?> out = mapper.mapMeetings(List.of(m));
        assertEquals(1, out.size());
    }

    @Test
    void mapChatbotTemplates_nullAndNonNull() {
        assertNull(mapper.mapChatbotTemplates(null));
        ChatbotTemplate c = new ChatbotTemplate();
        List<?> out = mapper.mapChatbotTemplates(List.of(c));
        assertEquals(1, out.size());
    }
}
