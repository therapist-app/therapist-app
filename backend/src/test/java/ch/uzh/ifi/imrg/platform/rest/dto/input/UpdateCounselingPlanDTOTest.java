package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class UpdateCounselingPlanDTOTest {

    @Test
    void gettersSettersEqualsHashCodeToString() {
        UpdateCounselingPlanDTO a = new UpdateCounselingPlanDTO();
        UpdateCounselingPlanDTO b = new UpdateCounselingPlanDTO();

        Instant t = Instant.now();
        a.setCounselingPlanId("cp1");
        a.setStartOfTherapy(t);
        b.setCounselingPlanId("cp1");
        b.setStartOfTherapy(t);

        assertEquals("cp1", a.getCounselingPlanId());
        assertEquals(t, a.getStartOfTherapy());
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertTrue(a.toString().contains("counselingPlanId"));

        b.setCounselingPlanId("cp2");
        assertNotEquals(a, b);
    }
}
