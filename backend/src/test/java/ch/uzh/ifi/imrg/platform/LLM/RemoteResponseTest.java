package ch.uzh.ifi.imrg.platform.LLM;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RemoteResponseTest {

    @Test
    void nestedClassesAndLombokMethods() {
        RemoteResponse.Message msg1 = new RemoteResponse.Message();
        msg1.setRole("r");
        msg1.setContent("c");
        assertEquals("r", msg1.getRole());
        assertEquals("c", msg1.getContent());
        String ms = msg1.toString();
        assertTrue(ms.contains("role"));
        assertTrue(ms.contains("content"));

        RemoteResponse.Choice choice1 = new RemoteResponse.Choice();
        choice1.setMessage(msg1);
        assertSame(msg1, choice1.getMessage());
        String cs = choice1.toString();
        assertTrue(cs.contains("message"));

        RemoteResponse resp1 = new RemoteResponse();
        resp1.setChoices(List.of(choice1));
        assertEquals(1, resp1.getChoices().size());
        assertSame(choice1, resp1.getChoices().get(0));
        String rs = resp1.toString();
        assertTrue(rs.contains("choices"));

        RemoteResponse.Message msg2 = new RemoteResponse.Message();
        msg2.setRole("r");
        msg2.setContent("c");
        RemoteResponse.Choice choice2 = new RemoteResponse.Choice();
        choice2.setMessage(msg2);
        RemoteResponse resp2 = new RemoteResponse();
        resp2.setChoices(List.of(choice2));
        assertEquals(resp1, resp2);
        assertEquals(resp1.hashCode(), resp2.hashCode());

        resp2.setChoices(List.of());
        assertNotEquals(resp1, resp2);
    }
}
