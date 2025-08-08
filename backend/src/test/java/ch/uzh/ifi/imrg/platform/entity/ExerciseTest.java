package ch.uzh.ifi.imrg.platform.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class ExerciseTest {

    @Test
    void therapistIdIsResolved() {
        Therapist th = mock(Therapist.class);
        when(th.getId()).thenReturn("T");
        Patient p = mock(Patient.class);
        when(p.getTherapist()).thenReturn(th);
        Exercise e = new Exercise();
        e.setPatient(p);
        assertEquals("T", e.getOwningTherapistId());
    }

    @Test
    void contextUsesStaticHelpers() {
        Exercise e = new Exercise();
        e.getExerciseComponents().add(new ExerciseComponent());
        try (MockedStatic<LLMContextBuilder> st = mockStatic(LLMContextBuilder.class)) {
            StringBuilder base = new StringBuilder("B");
            st.when(() -> LLMContextBuilder.getOwnProperties(e, 2)).thenReturn(base);
            st.when(() -> LLMContextBuilder.addLLMContextOfListOfEntities(any(), any(), eq("Exercise Component"), eq(2)))
                    .thenAnswer(inv -> {((StringBuilder)inv.getArgument(0)).append("|C"); return null;});
            assertEquals("B|C", e.toLLMContext(2));
            st.verify(() -> LLMContextBuilder.addLLMContextOfListOfEntities(base, e.getExerciseComponents(), "Exercise Component", 2));
        }
    }
}
