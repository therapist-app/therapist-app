package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TherapistChatbotInputDTOTest {

    @Test
    void gettersSettersToStringEqualsHashCode() {
        ChatMessageDTO m1 = new ChatMessageDTO(ChatRole.USER, "u");
        TherapistChatbotInputDTO dto1 = new TherapistChatbotInputDTO();
        dto1.setChatMessages(List.of(m1));
        dto1.setPatientId("p1");
        dto1.setLanguage(Language.English);
        assertEquals(List.of(m1), dto1.getChatMessages());
        assertEquals("p1", dto1.getPatientId());
        assertEquals(Language.English, dto1.getLanguage());
        String s = dto1.toString();
        assertTrue(s.contains("chatMessages"));
        assertTrue(s.contains("patientId"));
        assertTrue(s.contains("language"));

        TherapistChatbotInputDTO dto2 = new TherapistChatbotInputDTO();
        dto2.setChatMessages(List.of(m1));
        dto2.setPatientId("p1");
        dto2.setLanguage(Language.English);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setPatientId("p2");
        assertNotEquals(dto1, dto2);
    }
}
