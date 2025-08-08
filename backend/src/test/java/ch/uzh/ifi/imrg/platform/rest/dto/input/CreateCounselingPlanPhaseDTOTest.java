package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateCounselingPlanPhaseDTOTest {

    @Test
    void gettersSettersToStringEqualsHashCode() {
        CreateCounselingPlanPhaseDTO dto1 = new CreateCounselingPlanPhaseDTO();
        dto1.setCounselingPlanId("plan");
        dto1.setPhaseName("phase");
        dto1.setDurationInWeeks(5);
        assertEquals("plan", dto1.getCounselingPlanId());
        assertEquals("phase", dto1.getPhaseName());
        assertEquals(5, dto1.getDurationInWeeks());
        String s = dto1.toString();
        assertTrue(s.contains("counselingPlanId"));
        assertTrue(s.contains("phaseName"));
        assertTrue(s.contains("durationInWeeks"));

        CreateCounselingPlanPhaseDTO dto2 = new CreateCounselingPlanPhaseDTO();
        dto2.setCounselingPlanId("plan");
        dto2.setPhaseName("phase");
        dto2.setDurationInWeeks(5);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setDurationInWeeks(6);
        assertNotEquals(dto1, dto2);
    }
}
