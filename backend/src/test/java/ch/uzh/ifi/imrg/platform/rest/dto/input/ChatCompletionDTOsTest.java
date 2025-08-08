package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChatCompletionDTOsTest {

    @Test
    void chatCompletionRequestDTO() {
        ChatCompletionRequestDTO dto1 = new ChatCompletionRequestDTO();
        ChatMessageDTO m1 = new ChatMessageDTO(ChatRole.USER, "u");
        ChatMessageDTO m2 = new ChatMessageDTO(ChatRole.ASSISTANT, "a");
        dto1.setMessages(List.of(m1, m2));
        dto1.setLanguage(Language.German);
        assertEquals(List.of(m1, m2), dto1.getMessages());
        assertEquals(Language.German, dto1.getLanguage());
        String s = dto1.toString();
        assertTrue(s.contains("messages"));
        assertTrue(s.contains("language"));

        ChatCompletionRequestDTO dto2 = new ChatCompletionRequestDTO();
        dto2.setMessages(List.of(m1, m2));
        dto2.setLanguage(Language.German);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        dto2.setLanguage(Language.English);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void chatCompletionWithConfigRequestDTO() {
        ChatCompletionWithConfigRequestDTO dto1 = new ChatCompletionWithConfigRequestDTO();
        dto1.setTemplateId("t");
        ChatbotConfigDTO cfg = new ChatbotConfigDTO();
        dto1.setConfig(cfg);
        ChatMessageDTO h1 = new ChatMessageDTO(ChatRole.SYSTEM, "x");
        dto1.setHistory(List.of(h1));
        dto1.setMessage("m");
        dto1.setLanguage(Language.English);

        assertEquals("t", dto1.getTemplateId());
        assertSame(cfg, dto1.getConfig());
        assertEquals(List.of(h1), dto1.getHistory());
        assertEquals("m", dto1.getMessage());
        assertEquals(Language.English, dto1.getLanguage());
        String s = dto1.toString();
        assertTrue(s.contains("templateId"));
        assertTrue(s.contains("config"));
        assertTrue(s.contains("history"));
        assertTrue(s.contains("message"));
        assertTrue(s.contains("language"));

        ChatCompletionWithConfigRequestDTO dto2 = new ChatCompletionWithConfigRequestDTO();
        dto2.setTemplateId("t");
        dto2.setConfig(cfg);
        dto2.setHistory(List.of(h1));
        dto2.setMessage("m");
        dto2.setLanguage(Language.English);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        dto2.setMessage("other");
        assertNotEquals(dto1, dto2);
    }
}
