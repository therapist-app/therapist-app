package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class PsychologicalTestCreateDTOTest {

    @Test
    void gettersSetters() {
        PsychologicalTestCreateDTO dto = new PsychologicalTestCreateDTO();
        Instant s = Instant.now();
        Instant e = s.plusSeconds(60);

        dto.setPatientId("p1");
        dto.setTestName("GAD-7");
        dto.setExerciseStart(s);
        dto.setExerciseEnd(e);
        dto.setIsPaused(Boolean.FALSE);
        dto.setDoEveryNDays(5);

        assertEquals("p1", dto.getPatientId());
        assertEquals("GAD-7", dto.getTestName());
        assertEquals(s, dto.getExerciseStart());
        assertEquals(e, dto.getExerciseEnd());
        assertEquals(Boolean.FALSE, dto.getIsPaused());
        assertEquals(5, dto.getDoEveryNDays());
    }
}
