package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

class LLMUZHMappingTest {

    @Test
    void roleMapping() throws Exception {
        LLMUZH llm = new LLMUZH();
        Method m = LLMUZH.class.getDeclaredMethod("mapChatRoleToString", ChatRole.class);
        m.setAccessible(true);
        assertEquals("user", m.invoke(llm, ChatRole.USER));
        assertEquals("assistant", m.invoke(llm, ChatRole.ASSISTANT));
        assertEquals("system", m.invoke(llm, ChatRole.SYSTEM));
    }

    @Test
    void requestMessageMapping() throws Exception {
        LLMUZH llm = new LLMUZH();
        ChatMessageDTO dto = new ChatMessageDTO(ChatRole.SYSTEM, "test-content");
        Method m = LLMUZH.class.getDeclaredMethod("mapToRequestMessage", ChatMessageDTO.class);
        m.setAccessible(true);
        Object obj = m.invoke(llm, dto);
        assertTrue(obj instanceof RequestPayload.Message);
        RequestPayload.Message msg = (RequestPayload.Message) obj;
        assertEquals("system", msg.getRole());
        assertEquals("test-content", msg.getContent());
    }
}
