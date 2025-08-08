package ch.uzh.ifi.imrg.platform.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class ExerciseComponentTest {

    @Test
    void therapistIdIsResolved() {
        Therapist th = mock(Therapist.class);
        when(th.getId()).thenReturn("TID");
        Patient pa = mock(Patient.class);
        when(pa.getTherapist()).thenReturn(th);
        Exercise ex = mock(Exercise.class);
        when(ex.getPatient()).thenReturn(pa);

        ExerciseComponent ec = new ExerciseComponent();
        ec.setExercise(ex);

        assertEquals("TID", ec.getOwningTherapistId());
    }

    @Test
    void toLLMContextDelegates() {
        ExerciseComponent ec = new ExerciseComponent();
        try (MockedStatic<LLMContextBuilder> stat = mockStatic(LLMContextBuilder.class)) {
            StringBuilder sb = new StringBuilder("CTX");
            stat.when(() -> LLMContextBuilder.getOwnProperties(ec, 2)).thenReturn(sb);
            assertEquals("CTX", ec.toLLMContext(2));
            stat.verify(() -> LLMContextBuilder.getOwnProperties(ec, 2));
            stat.verifyNoMoreInteractions();
        }
    }
}
