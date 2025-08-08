package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddExerciseToCounselingPlanPhaseDTOTest {

    @Test
    void gettersSettersToStringEqualsHashCode() {
        AddExerciseToCounselingPlanPhaseDTO dto1 = new AddExerciseToCounselingPlanPhaseDTO();
        dto1.setExerciseId("e1");
        dto1.setCounselingPlanPhaseId("p1");
        assertEquals("e1", dto1.getExerciseId());
        assertEquals("p1", dto1.getCounselingPlanPhaseId());
        String s = dto1.toString();
        assertTrue(s.contains("exerciseId"));
        assertTrue(s.contains("counselingPlanPhaseId"));

        AddExerciseToCounselingPlanPhaseDTO dto2 = new AddExerciseToCounselingPlanPhaseDTO();
        dto2.setExerciseId("e1");
        dto2.setCounselingPlanPhaseId("p1");
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setCounselingPlanPhaseId("p2");
        assertNotEquals(dto1, dto2);
    }
}
