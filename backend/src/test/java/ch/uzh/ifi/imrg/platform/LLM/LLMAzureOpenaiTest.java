package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.List;
import static org.mockito.Mockito.*;

class LLMAzureOpenaiTest {

    private static ChatMessageDTO dto(ChatRole role, String content) {
        return new ChatMessageDTO(role, content);
    }

    @SuppressWarnings("unchecked")
    private static ChatCompletions realCompletions(List<ChatChoice> list) throws Exception {
        Field u = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        u.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) u.get(null);

        ChatCompletions completions = (ChatCompletions) unsafe.allocateInstance(ChatCompletions.class);

        for (Field f : ChatCompletions.class.getDeclaredFields()) {
            if (List.class.isAssignableFrom(f.getType())) {
                f.setAccessible(true);
                f.set(completions, list);
                break;
            }
        }
        return completions;
    }

    private static LLMAzureOpenai instance(OpenAIClient client) throws Exception {
        Field u = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        u.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) u.get(null);
        LLMAzureOpenai obj = (LLMAzureOpenai) unsafe.allocateInstance(LLMAzureOpenai.class);
        Field f = LLMAzureOpenai.class.getDeclaredField("client");
        f.setAccessible(true);
        f.set(obj, client);
        return obj;
    }

    @Test
    void nullCompletions() throws Exception {
        OpenAIClient client = mock(OpenAIClient.class);
        when(client.getChatCompletions(anyString(), any(ChatCompletionsOptions.class))).thenReturn(null);
        LLMAzureOpenai azure = instance(client);
        Assertions.assertThrows(RuntimeException.class, () ->
                azure.getRawLLMResponse(List.of(dto(ChatRole.USER, "hi")), Language.English));
    }

    @Test
    void emptyChoices() throws Exception {
        ChatCompletions completions = realCompletions(List.of());
        OpenAIClient client = mock(OpenAIClient.class);
        when(client.getChatCompletions(anyString(), any(ChatCompletionsOptions.class))).thenReturn(completions);
        LLMAzureOpenai azure = instance(client);
        Assertions.assertThrows(RuntimeException.class, () ->
                azure.getRawLLMResponse(List.of(dto(ChatRole.USER, "hi")), Language.English));
    }
}
