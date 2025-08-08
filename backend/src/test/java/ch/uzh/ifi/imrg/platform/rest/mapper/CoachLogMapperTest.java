package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.generated.model.LogOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.rest.dto.output.LogOutputDTO;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CoachLogMapperTest {

    @Test
    void apiToLocalSingleAndNullList() {
        Instant t = Instant.now();
        LogOutputDTOPatientAPI api = new LogOutputDTOPatientAPI();
        api.setId("id1");
        api.setPatientId("p1");
        api.setLogType(LogOutputDTOPatientAPI.LogTypeEnum.GENERAL_CONVERSATION_CREATION);
        api.setTimestamp(t);
        api.setUniqueIdentifier("u1");
        api.setComment("c1");

        LogOutputDTO mapped = CoachLogMapper.INSTANCE.apiToLocal(api);
        assertEquals("id1", mapped.getId());
        assertEquals("p1", mapped.getPatientId());
        assertEquals(LogOutputDTOPatientAPI.LogTypeEnum.GENERAL_CONVERSATION_CREATION.toString(), mapped.getLogType());
        assertEquals(t, mapped.getTimestamp());
        assertEquals("u1", mapped.getUniqueIdentifier());
        assertEquals("c1", mapped.getComment());

        assertNull(CoachLogMapper.INSTANCE.apiToLocal((List<LogOutputDTOPatientAPI>) null));
    }

    @Test
    void apiToLocalList() {
        LogOutputDTOPatientAPI a = new LogOutputDTOPatientAPI();
        a.setId("A");
        LogOutputDTOPatientAPI b = new LogOutputDTOPatientAPI();
        b.setId("B");
        List<LogOutputDTO> list = CoachLogMapper.INSTANCE.apiToLocal(List.of(a, b));
        assertEquals(2, list.size());
        assertEquals("A", list.get(0).getId());
        assertEquals("B", list.get(1).getId());
    }
}
