package ch.uzh.ifi.imrg.platform.rest.mapper;

import static org.junit.jupiter.api.Assertions.*;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import java.util.List;
import org.junit.jupiter.api.Test;

class CounselingPlanMapperTest {

    @Test
    void mapCounselingPlanPhasesReturnsNullOnNullInput() {
        assertNull(CounselingPlanMapper.INSTANCE.mapCounselingPlanPhases(null));
    }

    @Test
    void mapCounselingPlanPhasesMapsEachPhase() {
        List<CounselingPlanPhase> phases = List.of(new CounselingPlanPhase());

        var dtos = CounselingPlanMapper.INSTANCE.mapCounselingPlanPhases(phases);

        assertEquals(1, dtos.size());
        assertNotNull(dtos.get(0));
    }
}
