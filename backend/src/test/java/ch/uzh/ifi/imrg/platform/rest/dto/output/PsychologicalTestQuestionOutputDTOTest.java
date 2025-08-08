package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PsychologicalTestQuestionOutputDTOTest {

    @Test
    void gettersAndSetters() {
        PsychologicalTestQuestionOutputDTO dto = new PsychologicalTestQuestionOutputDTO();
        dto.setQuestion("Q1");
        dto.setScore(3);
        assertEquals("Q1", dto.getQuestion());
        assertEquals(3, dto.getScore());
    }
}
