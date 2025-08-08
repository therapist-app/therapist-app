package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExercisesOverviewOutputDTOTest {

    @Test
    void gettersAndSetters() {
        ExercisesOverviewOutputDTO dto = new ExercisesOverviewOutputDTO();
        dto.setId("ex1");
        dto.setExerciseTitle("Daily Walk");
        assertEquals("ex1", dto.getId());
        assertEquals("Daily Walk", dto.getExerciseTitle());
        assertTrue(dto.equals(dto));
    }
}
