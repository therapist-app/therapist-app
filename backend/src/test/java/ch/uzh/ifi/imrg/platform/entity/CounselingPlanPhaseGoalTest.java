package ch.uzh.ifi.imrg.platform.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class CounselingPlanPhaseGoalTest {

    @Test
    void owningTherapistIdExtracted() {
        Therapist therapist = mock(Therapist.class);
        when(therapist.getId()).thenReturn("TID");
        Patient patient = mock(Patient.class);
        when(patient.getTherapist()).thenReturn(therapist);
        CounselingPlan plan = mock(CounselingPlan.class);
        when(plan.getPatient()).thenReturn(patient);
        CounselingPlanPhase phase = mock(CounselingPlanPhase.class);
        when(phase.getCounselingPlan()).thenReturn(plan);

        CounselingPlanPhaseGoal goal = new CounselingPlanPhaseGoal();
        goal.setCounselingPlanPhase(phase);

        assertEquals("TID", goal.getOwningTherapistId());
    }

    @Test
    void toLLMContextUsesHelper() {
        CounselingPlanPhaseGoal goal = new CounselingPlanPhaseGoal();
        try (MockedStatic<LLMContextBuilder> stat = mockStatic(LLMContextBuilder.class)) {
            StringBuilder sb = new StringBuilder("X");
            stat.when(() -> LLMContextBuilder.getOwnProperties(goal, 3)).thenReturn(sb);
            assertEquals("X", goal.toLLMContext(3));
            stat.verify(() -> LLMContextBuilder.getOwnProperties(goal, 3));
            stat.verifyNoMoreInteractions();
        }
    }
}
