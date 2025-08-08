package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class LLMTest {

    @BeforeAll
    static void setUp() throws Exception {
        Field f = EnvironmentVariables.class.getDeclaredField("LLM_MAX_CHARACTERS");
        f.setAccessible(true);
        f.set(null, 1000);
    }

    private static ChatMessageDTO msg(ChatRole role, String content) {
        return new ChatMessageDTO(role, content);
    }

    static class Person { public String name; }

    static class StubLLM extends LLM {
        private final String response;
        StubLLM(String response) { this.response = response; }
        @Override
        protected String getRawLLMResponse(List<ChatMessageDTO> messages, Language language) { return response; }
        List<ChatMessageDTO> trunc(List<ChatMessageDTO> msgs) { return truncateMessages(msgs); }
        String filt(String c) { return filterResponseContent(c); }
    }

    @Test
    void directJson() {
        StubLLM llm = new StubLLM("{\"name\":\"John\"}");
        Person p = llm.callLLMForObject(List.of(msg(ChatRole.USER, "hi")), Person.class, null);
        Assertions.assertEquals("John", p.name);
    }

    @Test
    void repairedJson() {
        StubLLM llm = new StubLLM("{\"name\":\"Jane");
        Person p = llm.callLLMForObject(List.of(msg(ChatRole.USER, "hi")), Person.class, null);
        Assertions.assertEquals("Jane", p.name);
    }

    @Test
    void unparsableJson() {
        StubLLM llm = new StubLLM("invalid");
        Assertions.assertThrows(RuntimeException.class, () ->
                llm.callLLMForObject(List.of(msg(ChatRole.USER, "hi")), Person.class, null));
    }

    @Test
    void filter() {
        StubLLM llm = new StubLLM("");
        String filtered = llm.filt("<think>abc</think>```json {\"a\":1} ```");
        Assertions.assertEquals("{\"a\":1}", filtered);
    }

    @Test
    void truncate() {
        int limit = EnvironmentVariables.LLM_MAX_CHARACTERS;
        String big = "x".repeat(limit + 50);
        List<ChatMessageDTO> msgs = new ArrayList<>();
        msgs.add(msg(ChatRole.SYSTEM, big));
        msgs.add(msg(ChatRole.ASSISTANT, big));
        msgs.add(msg(ChatRole.USER, big));
        StubLLM llm = new StubLLM("{}");
        List<ChatMessageDTO> result = llm.trunc(msgs);
        int total = result.stream().mapToInt(m -> m.getContent().length()).sum();
        Assertions.assertTrue(total <= limit);
        Assertions.assertEquals(ChatRole.SYSTEM, result.get(0).getChatRole());
        Assertions.assertEquals(ChatRole.USER, result.get(result.size() - 1).getChatRole());
    }

    @Test
    void enhanceSystem() {
        StubLLM llm = new StubLLM("{}");
        List<ChatMessageDTO> msgs = new ArrayList<>();
        msgs.add(msg(ChatRole.SYSTEM, "original"));
        llm.enhanceSystemMessage(msgs, Language.German);
        String content = msgs.get(0).getContent();
        Assertions.assertTrue(content.contains("original"));
        Assertions.assertTrue(content.contains(Language.German.toString()));
    }
}
