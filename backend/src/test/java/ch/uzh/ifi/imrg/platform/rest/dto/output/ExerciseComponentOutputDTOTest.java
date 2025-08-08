package ch.uzh.ifi.imrg.platform.rest.dto.output;

import static org.junit.jupiter.api.Assertions.*;

import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;
import org.junit.jupiter.api.Test;

class ExerciseComponentOutputDTOTest {

    @Test
    void gettersAndSetters() {
        ExerciseComponentOutputDTO dto = new ExerciseComponentOutputDTO();
        dto.setId("ID");
        dto.setExerciseComponentType(ExerciseComponentType.FILE);
        dto.setExerciseComponentDescription("d");
        dto.setYoutubeUrl("yt");
        dto.setFileName("f");
        dto.setFileType("t");
        dto.setExtractedText("txt");
        dto.setOrderNumber(3);

        assertEquals("ID", dto.getId());
        assertEquals(ExerciseComponentType.FILE, dto.getExerciseComponentType());
        assertEquals("d", dto.getExerciseComponentDescription());
        assertEquals("yt", dto.getYoutubeUrl());
        assertEquals("f", dto.getFileName());
        assertEquals("t", dto.getFileType());
        assertEquals("txt", dto.getExtractedText());
        assertEquals(3, dto.getOrderNumber());
    }
}
