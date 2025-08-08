package ch.uzh.ifi.imrg.platform.rest.dto.output;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class CounselingPlanOutputDTOTest {

    @Test
    void allGeneratedMethodsBehaveAsExpected() {
        CounselingPlanOutputDTO a = new CounselingPlanOutputDTO();
        a.setId("1");
        a.setCreatedAt(Instant.parse("2024-01-01T00:00:00Z"));
        a.setUpdatedAt(Instant.parse("2024-01-02T00:00:00Z"));
        a.setStartOfTherapy(Instant.parse("2024-02-01T00:00:00Z"));
        a.setCounselingPlanPhasesOutputDTO(List.of(new CounselingPlanPhaseOutputDTO()));

        assertEquals("1", a.getId());
        assertEquals(Instant.parse("2024-01-01T00:00:00Z"), a.getCreatedAt());
        assertEquals(Instant.parse("2024-01-02T00:00:00Z"), a.getUpdatedAt());
        assertEquals(Instant.parse("2024-02-01T00:00:00Z"), a.getStartOfTherapy());
        assertEquals(1, a.getCounselingPlanPhasesOutputDTO().size());

        assertEquals(a, a);

        assertNotEquals(a, null);
        assertNotEquals(a, "something else");

        CounselingPlanOutputDTO b = new CounselingPlanOutputDTO();
        b.setId("1");
        b.setCreatedAt(a.getCreatedAt());
        b.setUpdatedAt(a.getUpdatedAt());
        b.setStartOfTherapy(a.getStartOfTherapy());
        b.setCounselingPlanPhasesOutputDTO(a.getCounselingPlanPhasesOutputDTO());

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        b.setId("2");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());

        String txt = a.toString();
        assertTrue(txt.contains("CounselingPlanOutputDTO"));
        assertTrue(txt.contains("id=1"));
    }
}
