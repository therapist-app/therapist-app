package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseInformationOutputDTOTest {

    @Test
    void gettersSetters() {
        ExerciseInformationOutputDTO dto = new ExerciseInformationOutputDTO();
        Instant s = Instant.now();
        Instant e = s.plusSeconds(120);
        dto.setStartTime(s);
        dto.setEndTime(e);
        dto.setFeedback("fb");
        dto.setSharedInputFields(List.<SharedInputFieldOutputDTO>of());
        dto.setMoodsBefore(List.<ExerciseMoodOutputDTO>of());
        dto.setMoodsAfter(List.<ExerciseMoodOutputDTO>of());

        assertEquals(s, dto.getStartTime());
        assertEquals(e, dto.getEndTime());
        assertEquals("fb", dto.getFeedback());
        assertNotNull(dto.getSharedInputFields());
        assertNotNull(dto.getMoodsBefore());
        assertNotNull(dto.getMoodsAfter());
    }
}
