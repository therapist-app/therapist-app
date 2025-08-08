package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PsychologicalTestOutputDTOTest {

    @Test
    void constructorAndGettersSetters() {
        PsychologicalTestOutputDTO dto = new PsychologicalTestOutputDTO("PHQ-9");

        dto.setId("id1");
        dto.setDescription("desc");
        dto.setPatientId("p1");
        Instant completed = Instant.now();
        dto.setCompletedAt(completed);
        PsychologicalTestQuestionOutputDTO q = new PsychologicalTestQuestionOutputDTO();
        dto.setQuestions(List.of(q));

        assertEquals("PHQ-9", dto.getName());
        assertEquals("id1", dto.getId());
        assertEquals("desc", dto.getDescription());
        assertEquals("p1", dto.getPatientId());
        assertEquals(completed, dto.getCompletedAt());
        assertEquals(1, dto.getQuestions().size());
    }
}
