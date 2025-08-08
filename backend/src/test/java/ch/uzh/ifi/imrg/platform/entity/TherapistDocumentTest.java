package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class TherapistDocumentTest {

    @Test
    void owningTherapistId_and_toLLMContext_areCorrect() {
        Therapist therapist = new Therapist();
        therapist.setId(UUID.randomUUID().toString());

        TherapistDocument doc = new TherapistDocument();
        doc.setTherapist(therapist);

        try (MockedStatic<LLMContextBuilder> st =
                     Mockito.mockStatic(LLMContextBuilder.class)) {

            st.when(() -> LLMContextBuilder.getOwnProperties(doc, 7))
                    .thenAnswer(inv -> new StringBuilder("CTX"));

            assertEquals(therapist.getId(), doc.getOwningTherapistId());
            assertEquals("CTX", doc.toLLMContext(7));

            st.verify(() -> LLMContextBuilder.getOwnProperties(doc, 7));
        }
    }
}
