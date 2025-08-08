package ch.uzh.ifi.imrg.platform.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CounselingPlanTest {

    @Test
    void getOwningTherapistIdReturnsIdWhenPatientPresent() {
        Therapist therapist = mock(Therapist.class);
        when(therapist.getId()).thenReturn("therapist-id");

        Patient patient = mock(Patient.class);
        when(patient.getTherapist()).thenReturn(therapist);

        CounselingPlan plan = new CounselingPlan();
        plan.setPatient(patient);

        Instant now = Instant.now();
        plan.setUpdatedAt(now);
        plan.setStartOfTherapy(now);

        assertEquals(now, plan.getUpdatedAt());
        assertEquals(now, plan.getStartOfTherapy());

        assertEquals("therapist-id", plan.getOwningTherapistId());
    }

    @Test
    void getOwningTherapistIdThrowsWhenPatientMissing() {
        CounselingPlan plan = new CounselingPlan();
        assertThrows(NullPointerException.class, plan::getOwningTherapistId);
    }

    @Test
    void toLlmContextDelegatesToBuilderAndReturnsCombinedString() {
        CounselingPlan plan = new CounselingPlan();
        plan.setStartOfTherapy(Instant.now());

        try (MockedStatic<LLMContextBuilder> mocked = mockStatic(LLMContextBuilder.class)) {
            StringBuilder sb = new StringBuilder("base");
            mocked.when(() -> LLMContextBuilder.getOwnProperties(plan, 2)).thenReturn(sb);
            mocked.when(() -> LLMContextBuilder.addLLMContextOfListOfEntities(
                            sb,
                            plan.getCounselingPlanPhases(),
                            "Counseling Plan Phase",
                            2))
                    .thenAnswer(inv -> { sb.append("|phases"); return null; });

            String result = plan.toLLMContext(2);

            assertEquals("base|phases", result);
            mocked.verify(() -> LLMContextBuilder.getOwnProperties(plan, 2));
            mocked.verify(() -> LLMContextBuilder.addLLMContextOfListOfEntities(
                    sb,
                    plan.getCounselingPlanPhases(),
                    "Counseling Plan Phase",
                    2));
        }
    }
}
