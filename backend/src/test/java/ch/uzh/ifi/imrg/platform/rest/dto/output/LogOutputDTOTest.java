package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class LogOutputDTOTest {

    @Test
    void gettersAndSetters() {
        LogOutputDTO dto = new LogOutputDTO();
        Instant t = Instant.now();
        dto.setId("l1");
        dto.setPatientId("p1");
        dto.setLogType("INFO");
        dto.setTimestamp(t);
        dto.setUniqueIdentifier("u1");
        dto.setComment("c");
        assertEquals("l1", dto.getId());
        assertEquals("p1", dto.getPatientId());
        assertEquals("INFO", dto.getLogType());
        assertEquals(t, dto.getTimestamp());
        assertEquals("u1", dto.getUniqueIdentifier());
        assertEquals("c", dto.getComment());
        assertTrue(dto.equals(dto));
    }
}
