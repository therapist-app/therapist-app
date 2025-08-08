package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientTest {

    @Test
    void owningTherapistId_returnsTherapistId() {
        Therapist t = new Therapist();
        t.setId("tid");
        Patient p = new Patient();
        p.setTherapist(t);
        assertEquals("tid", p.getOwningTherapistId());
    }

    @Test
    void toLLMContext_withoutAndWithPlan() {
        try (MockedStatic<LLMContextBuilder> llm = Mockito.mockStatic(LLMContextBuilder.class);
             MockedStatic<FormatUtil> fmt = Mockito.mockStatic(FormatUtil.class)) {

            llm.when(() -> LLMContextBuilder.getOwnProperties(any(), anyInt()))
                    .thenAnswer(i -> new StringBuilder("P"));
            llm.when(
                            () ->
                                    LLMContextBuilder.addLLMContextOfListOfEntities(
                                            any(StringBuilder.class), anyList(), anyString(), anyInt()))
                    .thenAnswer(i -> null);
            fmt.when(() -> FormatUtil.indentBlock("\n--- Counseling Plan ---\n", 0, false))
                    .thenReturn("INDENT");

            CounselingPlan cp = mock(CounselingPlan.class);
            when(cp.getCounselingPlanPhases()).thenReturn(List.of());
            Patient p = new Patient();
            p.setCounselingPlan(cp);
            assertEquals("P", p.toLLMContext(0));

            CounselingPlan cp2 = mock(CounselingPlan.class);
            CounselingPlanPhase phase = mock(CounselingPlanPhase.class);
            when(cp2.getCounselingPlanPhases()).thenReturn(List.of(phase));
            when(cp2.toLLMContext(1)).thenReturn("CPCTX");
            Patient p2 = new Patient();
            p2.setCounselingPlan(cp2);
            assertEquals("PINDENTCPCTX", p2.toLLMContext(0));

        }
    }
}
