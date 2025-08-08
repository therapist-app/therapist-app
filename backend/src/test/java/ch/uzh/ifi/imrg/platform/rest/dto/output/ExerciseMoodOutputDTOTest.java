package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseMoodOutputDTOTest {

    @Test
    void gettersAndSetters() {
        ExerciseMoodOutputDTO dto = new ExerciseMoodOutputDTO();
        dto.setMoodName("Calm");
        dto.setMoodScore(7);
        assertEquals("Calm", dto.getMoodName());
        assertEquals(7, dto.getMoodScore());
        assertTrue(dto.equals(dto));
    }
}
