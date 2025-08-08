package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseComponentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseMapperTest {

    @Test
    void convertEntityToExerciseOutputDTOAndMapComponents() {
        Instant s = Instant.now();
        Instant e = s.plusSeconds(60);

        ExerciseComponent comp = new ExerciseComponent();
        comp.setId("c1");

        Exercise ex = new Exercise();
        ex.setId("ex1");
        ex.setExerciseTitle("title");
        ex.setExerciseDescription("desc");
        ex.setExerciseExplanation("expl");
        ex.setExerciseStart(s);
        ex.setExerciseEnd(e);
        ex.setIsPaused(Boolean.TRUE);
        ex.setDoEveryNDays(3);
        ex.setExerciseComponents(List.of(comp));

        ExerciseOutputDTO dto = ExerciseMapper.INSTANCE.convertEntityToExerciseOutputDTO(ex);
        assertEquals("ex1", dto.getId());
        assertEquals("title", dto.getExerciseTitle());
        assertEquals("desc", dto.getExerciseDescription());
        assertEquals("expl", dto.getExerciseExplanation());
        assertEquals(s, dto.getExerciseStart());
        assertEquals(e, dto.getExerciseEnd());
        assertEquals(Boolean.TRUE, dto.getIsPaused());
        assertEquals(3, dto.getDoEveryNDays());
        assertNotNull(dto.getExerciseComponentsOutputDTO());
        assertEquals(1, dto.getExerciseComponentsOutputDTO().size());

        List<ExerciseComponentOutputDTO> mapped = ExerciseMapper.INSTANCE.mapExerciseComponents(List.of(comp));
        assertEquals(1, mapped.size());
        assertEquals("c1", mapped.get(0).getId());

        assertNull(ExerciseMapper.INSTANCE.mapExerciseComponents(null));
    }
}
