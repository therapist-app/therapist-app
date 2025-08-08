package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class GetConversationSummaryInputDTOTest {

    @Test
    void gettersSetters() {
        GetConversationSummaryInputDTO dto = new GetConversationSummaryInputDTO();
        Instant start = Instant.now();
        Instant end = start.plusSeconds(60);
        dto.setStart(start);
        dto.setEnd(end);
        assertEquals(start, dto.getStart());
        assertEquals(end, dto.getEnd());
    }
}
