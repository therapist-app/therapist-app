package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TherapistChatbotOutputDTOTest {

    @Test
    void gettersSettersEqualsHashCodeToString() {
        TherapistChatbotOutputDTO a = new TherapistChatbotOutputDTO();
        a.setContent("text");

        TherapistChatbotOutputDTO b = new TherapistChatbotOutputDTO();
        b.setContent("text");

        assertEquals("text", a.getContent());
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
    }
}
