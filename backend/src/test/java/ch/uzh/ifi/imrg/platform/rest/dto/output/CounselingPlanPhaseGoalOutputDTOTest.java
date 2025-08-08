package ch.uzh.ifi.imrg.platform.rest.dto.output;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CounselingPlanPhaseGoalOutputDTOTest {

    @Test
    void gettersSettersEqualsHashCodeToString() {
        CounselingPlanPhaseGoalOutputDTO a = new CounselingPlanPhaseGoalOutputDTO();
        a.setId("1");
        a.setGoalName("Sleep hygiene");
        a.setGoalDescription("Create bedtime routine");
        a.setIsCompleted(false);

        assertEquals("1", a.getId());
        assertEquals("Sleep hygiene", a.getGoalName());
        assertEquals("Create bedtime routine", a.getGoalDescription());
        assertFalse(a.getIsCompleted());

        CounselingPlanPhaseGoalOutputDTO b = new CounselingPlanPhaseGoalOutputDTO();
        b.setId("1");
        b.setGoalName("Sleep hygiene");
        b.setGoalDescription("Create bedtime routine");
        b.setIsCompleted(false);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertTrue(a.toString().contains("Sleep hygiene"));

        b.setGoalDescription("Different");
        assertNotEquals(a, b);
    }
}
