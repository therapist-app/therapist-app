package ch.uzh.ifi.imrg.platform.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class CounselingPlanPhaseTest {

    @Test
    void returnsNestedTherapistId() {
        Therapist therapist = Mockito.mock(Therapist.class);
        when(therapist.getId()).thenReturn("THERA-42");

        Patient patient = Mockito.mock(Patient.class);
        when(patient.getTherapist()).thenReturn(therapist);

        CounselingPlan plan = Mockito.mock(CounselingPlan.class);
        when(plan.getPatient()).thenReturn(patient);

        CounselingPlanPhase phase = new CounselingPlanPhase();
        phase.setCounselingPlan(plan);

        assertEquals("THERA-42", phase.getOwningTherapistId());
    }

    @Test
    void buildsContextViaStaticHelpers() {
        CounselingPlanPhase phase = new CounselingPlanPhase();
        phase.getPhaseGoals().add(new CounselingPlanPhaseGoal());
        phase.getPhaseExercises().add(new Exercise());

        try (MockedStatic<LLMContextBuilder> stat = mockStatic(LLMContextBuilder.class)) {

            StringBuilder base = new StringBuilder("BASE");
            stat.when(() -> LLMContextBuilder.getOwnProperties(phase, 1)).thenReturn(base);

            stat
                    .when(
                            () ->
                                    LLMContextBuilder.addLLMContextOfListOfEntities(
                                            any(StringBuilder.class),
                                            any(List.class),
                                            eq("Counseling Plan Phase Goal"),
                                            eq(1)))
                    .thenAnswer(
                            inv -> {
                                ((StringBuilder) inv.getArgument(0)).append("|GOALS");
                                return null;
                            });

            stat
                    .when(
                            () ->
                                    LLMContextBuilder.addLLMContextOfListOfEntities(
                                            any(StringBuilder.class),
                                            any(List.class),
                                            eq("Counseling Plan Exercise"),
                                            eq(1)))
                    .thenAnswer(
                            inv -> {
                                ((StringBuilder) inv.getArgument(0)).append("|EXS");
                                return null;
                            });

            String ctx = phase.toLLMContext(1);
            assertEquals("BASE|GOALS|EXS", ctx);

            stat.verify(() -> LLMContextBuilder.getOwnProperties(phase, 1));
            stat.verify(
                    () ->
                            LLMContextBuilder.addLLMContextOfListOfEntities(
                                    base, phase.getPhaseGoals(), "Counseling Plan Phase Goal", 1));
            stat.verify(
                    () ->
                            LLMContextBuilder.addLLMContextOfListOfEntities(
                                    base, phase.getPhaseExercises(), "Counseling Plan Exercise", 1));

            stat.verifyNoMoreInteractions();
        }
    }
}
