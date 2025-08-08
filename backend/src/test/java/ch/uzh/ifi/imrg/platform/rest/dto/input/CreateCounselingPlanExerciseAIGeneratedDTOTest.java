package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateCounselingPlanExerciseAIGeneratedDTOTest {

    @Test
    void gettersSettersToStringEqualsHashCode() {
        CreateCounselingPlanExerciseAIGeneratedDTO dto1 = new CreateCounselingPlanExerciseAIGeneratedDTO();
        dto1.setCounselingPlanPhaseId("phase");
        dto1.setLanguage(Language.German);
        assertEquals("phase", dto1.getCounselingPlanPhaseId());
        assertEquals(Language.German, dto1.getLanguage());
        String s = dto1.toString();
        assertTrue(s.contains("counselingPlanPhaseId"));
        assertTrue(s.contains("language"));

        CreateCounselingPlanExerciseAIGeneratedDTO dto2 = new CreateCounselingPlanExerciseAIGeneratedDTO();
        dto2.setCounselingPlanPhaseId("phase");
        dto2.setLanguage(Language.German);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setLanguage(Language.English);
        assertNotEquals(dto1, dto2);
    }
}
