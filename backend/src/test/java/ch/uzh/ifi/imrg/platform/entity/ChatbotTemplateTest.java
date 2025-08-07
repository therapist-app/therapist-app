package ch.uzh.ifi.imrg.platform.entity;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ChatbotTemplateTest {

    private static final Instant TS = Instant.parse("2025-08-07T20:00:00Z");

    private ChatbotTemplate freshTemplate() {
        ChatbotTemplate tpl = new ChatbotTemplate();
        tpl.setChatbotName("CB");
        tpl.setChatbotRole("role");
        tpl.setChatbotTone("friendly");
        tpl.setWelcomeMessage("hi");
        tpl.setActive(true);

        Therapist ther = new Therapist();
        ther.setId("ther-123");
        tpl.setTherapist(ther);

        ChatbotTemplateDocument doc = new ChatbotTemplateDocument();
        doc.setId("doc-1");
        doc.setCreatedAt(TS);
        doc.setChatbotTemplate(tpl);

        tpl.setChatbotTemplateDocuments(List.of(doc));
        return tpl;
    }

    @Nested
    @DisplayName("getOwningTherapistId()")
    class OwningTherapist {

        @Test
        @DisplayName("returns the therapist id when set (happy-path branch)")
        void returnsId() {
            ChatbotTemplate tpl = freshTemplate();
            assertThat(tpl.getOwningTherapistId()).isEqualTo("ther-123");
        }

        @Test
        @DisplayName("throws NullPointerException when therapist is null (error branch)")
        void therapistNull() {
            ChatbotTemplate tpl = freshTemplate();
            tpl.setTherapist(null);
            assertThatThrownBy(tpl::getOwningTherapistId).isInstanceOf(NullPointerException.class);
        }
    }

    @Test
    @DisplayName("toLLMContext() delegates to LLMContextBuilder for *both* helper calls")
    void toLLMContextDelegates() {
        ChatbotTemplate tpl = freshTemplate();

        try (MockedStatic<LLMContextBuilder> mocked = mockStatic(LLMContextBuilder.class)) {

            StringBuilder sb = new StringBuilder("ownProps;");
            mocked.when(() -> LLMContextBuilder.getOwnProperties(eq(tpl), eq(0)))
                    .thenReturn(sb);

            mocked.when(() -> LLMContextBuilder
                            .addLLMContextOfListOfEntities(same(sb),
                                    eq(tpl.getChatbotTemplateDocuments()),
                                    eq("Chatbot Document"),
                                    eq(0)))
                    .thenAnswer(inv -> {
                        ((StringBuilder) inv.getArgument(0)).append("docs;");
                        return null;
                    });

            String result = tpl.toLLMContext(0);

            mocked.verify(() -> LLMContextBuilder.getOwnProperties(tpl, 0));
            mocked.verify(() -> LLMContextBuilder
                    .addLLMContextOfListOfEntities(sb,
                            tpl.getChatbotTemplateDocuments(),
                            "Chatbot Document",
                            0));

            assertThat(result).isEqualTo("ownProps;docs;");
        }
    }
}
