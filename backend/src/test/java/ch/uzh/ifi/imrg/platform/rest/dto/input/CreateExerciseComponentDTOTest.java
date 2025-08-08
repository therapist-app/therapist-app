package ch.uzh.ifi.imrg.platform.rest.dto.input;

import static org.junit.jupiter.api.Assertions.*;

import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;
import org.junit.jupiter.api.Test;

class CreateExerciseComponentDTOTest {

    @Test
    void gettersAndSetters() {
        CreateExerciseComponentDTO dto = new CreateExerciseComponentDTO();
        dto.setExerciseId("E");
        dto.setExerciseComponentDescription("desc");
        dto.setYoutubeUrl("yt");
        dto.setExerciseComponentType(ExerciseComponentType.TEXT);

        assertEquals("E", dto.getExerciseId());
        assertEquals("desc", dto.getExerciseComponentDescription());
        assertEquals("yt", dto.getYoutubeUrl());
        assertEquals(ExerciseComponentType.TEXT, dto.getExerciseComponentType());
    }
}
