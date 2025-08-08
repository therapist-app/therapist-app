package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseOutputDTOTest {

    @Test
    void dataMethods() {
        ExerciseOutputDTO a = new ExerciseOutputDTO();
        ExerciseOutputDTO b = new ExerciseOutputDTO();

        Instant s = Instant.now();
        Instant e = s.plusSeconds(300);

        ExerciseComponentOutputDTO comp = new ExerciseComponentOutputDTO();

        a.setId("id1");
        a.setExerciseTitle("title");
        a.setExerciseDescription("desc");
        a.setExerciseExplanation("expl");
        a.setExerciseStart(s);
        a.setExerciseEnd(e);
        a.setIsPaused(Boolean.FALSE);
        a.setDoEveryNDays(3);
        a.setExerciseComponentsOutputDTO(List.of(comp));

        b.setId("id1");
        b.setExerciseTitle("title");
        b.setExerciseDescription("desc");
        b.setExerciseExplanation("expl");
        b.setExerciseStart(s);
        b.setExerciseEnd(e);
        b.setIsPaused(Boolean.FALSE);
        b.setDoEveryNDays(3);
        b.setExerciseComponentsOutputDTO(List.of(comp));

        assertEquals("id1", a.getId());
        assertEquals("title", a.getExerciseTitle());
        assertEquals("desc", a.getExerciseDescription());
        assertEquals("expl", a.getExerciseExplanation());
        assertEquals(s, a.getExerciseStart());
        assertEquals(e, a.getExerciseEnd());
        assertEquals(Boolean.FALSE, a.getIsPaused());
        assertEquals(3, a.getDoEveryNDays());
        assertEquals(1, a.getExerciseComponentsOutputDTO().size());
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());

        b.setExerciseTitle("other");
        assertNotEquals(a, b);
    }
}
