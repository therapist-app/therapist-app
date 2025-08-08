package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SharedInputFieldOutputDTOTest {

    @Test
    void gettersAndSetters() {
        SharedInputFieldOutputDTO dto = new SharedInputFieldOutputDTO();
        dto.setExerciseComponentId("ec1");
        dto.setUserInput("text");
        assertEquals("ec1", dto.getExerciseComponentId());
        assertEquals("text", dto.getUserInput());
    }
}
