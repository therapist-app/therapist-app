package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatCompletionResponseDTOTest {

    @Test
    void constructorGettersEqualsHashCodeToString() {
        ChatCompletionResponseDTO a = new ChatCompletionResponseDTO("hello");
        ChatCompletionResponseDTO b = new ChatCompletionResponseDTO("hello");

        assertEquals("hello", a.getContent());
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertTrue(a.toString().contains("content"));

        ChatCompletionResponseDTO c = new ChatCompletionResponseDTO("bye");
        assertNotEquals(a, c);
    }
}
