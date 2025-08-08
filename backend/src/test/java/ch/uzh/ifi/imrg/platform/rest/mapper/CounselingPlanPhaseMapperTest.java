package ch.uzh.ifi.imrg.platform.rest.mapper;

import static org.junit.jupiter.api.Assertions.*;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import java.util.List;
import org.junit.jupiter.api.Test;

class CounselingPlanPhaseMapperTest {

    @Test
    void mapsWhenListsNull() {
        CounselingPlanPhase phase = new CounselingPlanPhase();
        phase.setPhaseName("Assessment");
        phase.setDurationInWeeks(4);
        phase.setPhaseNumber(1);
        phase.setPhaseGoals(null);
        phase.setPhaseExercises(null);
        CounselingPlanPhaseOutputDTO dto =
                CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(phase);
        assertNull(dto.getPhaseGoalsOutputDTO());
        assertNull(dto.getPhaseExercisesOutputDTO());
    }

    @Test
    void mapsWithValues() {
        CounselingPlanPhaseGoal goal = new CounselingPlanPhaseGoal();
        Exercise ex = new Exercise();
        CounselingPlanPhase phase = new CounselingPlanPhase();
        phase.setPhaseName("Intervention");
        phase.setDurationInWeeks(6);
        phase.setPhaseNumber(2);
        phase.setPhaseGoals(List.of(goal));
        phase.setPhaseExercises(List.of(ex));
        CounselingPlanPhaseOutputDTO dto =
                CounselingPlanPhaseMapper.INSTANCE.convertEntityToCounselingPlanPhaseOutputDTO(phase);
        assertEquals(1, dto.getPhaseGoalsOutputDTO().size());
        assertEquals(1, dto.getPhaseExercisesOutputDTO().size());
    }
}
