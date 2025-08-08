package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TherapistTest {

    @Test
    void toLLMContext_callsBuilderForAllParts() {
        Therapist th = new Therapist();
        th.setPatients(Collections.emptyList());
        th.setTherapistDocuments(Collections.emptyList());

        try (MockedStatic<LLMContextBuilder> st = Mockito.mockStatic(LLMContextBuilder.class)) {
            st.when(() -> LLMContextBuilder.getOwnProperties(th, 0))
                    .thenAnswer(inv -> new StringBuilder("BASE"));

            st.when(() ->
                            LLMContextBuilder.addLLMContextOfListOfEntities(
                                    any(), any(List.class), anyString(), anyInt()))
                    .thenAnswer(inv -> {
                        StringBuilder sb = inv.getArgument(0);
                        sb.append('_').append(inv.<String>getArgument(2));
                        return null;
                    });

            assertEquals("BASE_Client_Coach Document", th.toLLMContext(0));

            st.verify(() -> LLMContextBuilder.getOwnProperties(th, 0));
            st.verify(() ->
                    LLMContextBuilder.addLLMContextOfListOfEntities(
                            any(), any(List.class), eq("Client"), eq(0)));
            st.verify(() ->
                    LLMContextBuilder.addLLMContextOfListOfEntities(
                            any(), any(List.class), eq("Coach Document"), eq(0)));
        }
    }

    @Test
    void lombokGeneratedMethods_areCorrect() {
        Instant now = Instant.now();

        Therapist t1 = new Therapist();
        t1.setEmail("mail@domain.ch");
        t1.setPassword("pw");
        t1.setLlmModel(LLMModel.LOCAL_UZH);
        t1.setPatients(Collections.emptyList());
        t1.setChatbotTemplates(Collections.emptyList());
        t1.setTherapistDocuments(Collections.emptyList());
        t1.setCreatedAt(now);
        t1.setUpdatedAt(now.plusSeconds(5));

        assertEquals("mail@domain.ch",           t1.getEmail());
        assertEquals("pw",                       t1.getPassword());
        assertEquals(LLMModel.LOCAL_UZH,         t1.getLlmModel());
        assertEquals(0,                          t1.getPatients().size());
        assertEquals(0,                          t1.getChatbotTemplates().size());
        assertEquals(0,                          t1.getTherapistDocuments().size());
        assertEquals(now,                        t1.getCreatedAt());
        assertEquals(now.plusSeconds(5),         t1.getUpdatedAt());
        assertNotNull(                           t1.getId());

        Therapist t2 = new Therapist();
        t2.setId(t1.getId());
        t2.setEmail("mail@domain.ch");
        t2.setPassword("pw");
        t2.setLlmModel(LLMModel.LOCAL_UZH);
        t2.setPatients(Collections.emptyList());
        t2.setChatbotTemplates(Collections.emptyList());
        t2.setTherapistDocuments(Collections.emptyList());
        t2.setCreatedAt(now);
        t2.setUpdatedAt(now.plusSeconds(5));

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());

        assertTrue(t1.toString().contains("mail@domain.ch"));
    }
}
