package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.platform.LLM.LLM;
import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentSummarizerUtilTest {

    static class StubLLM extends LLM {
        private int calls;
        private final int failCount;
        private final String content;
        StubLLM(int failCount, String content) { this.failCount = failCount; this.content = content; }
        @Override
        protected String getRawLLMResponse(List<ChatMessageDTO> messages, Language language) {
            calls++;
            if (calls <= failCount) throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            return content;
        }
    }

    @AfterEach
    void clearInterrupt() {
        Thread.interrupted();
    }

    @Test
    void summarizeUsesFactoryAndTrims() {
        EnvironmentVariables.LLM_MAX_CHARACTERS = 200000;
        TherapistRepository repo = mock(TherapistRepository.class);
        Therapist t = mock(Therapist.class);
        when(t.getId()).thenReturn("T1");
        when(t.getLlmModel()).thenReturn(LLMModel.AZURE_OPENAI);
        when(repo.findById("T1")).thenReturn(Optional.of(t));

        String longOut = "x".repeat(5000);
        try (MockedStatic<LLMFactory> ms = mockStatic(LLMFactory.class)) {
            ms.when(() -> LLMFactory.getInstance(LLMModel.AZURE_OPENAI)).thenReturn(new StubLLM(0, longOut));
            String fullText = "short text";
            String res = DocumentSummarizerUtil.summarize(fullText, t, repo);
            assertEquals(3283, res.length());
        }
    }

    @Test
    void buildChunksPageAndNoPage() throws Exception {
        Method m = DocumentSummarizerUtil.class.getDeclaredMethod("buildChunks", String.class, int.class, int.class);
        m.setAccessible(true);

        String withPages = "a\fb\fc";
        @SuppressWarnings("unchecked")
        List<String> chunks1 = (List<String>) m.invoke(null, withPages, 1, 3);
        assertEquals(List.of("a", "b", "c"), chunks1);

        String noPages = "abcdef";
        @SuppressWarnings("unchecked")
        List<String> chunks2 = (List<String>) m.invoke(null, noPages, 2, 3);
        assertEquals(List.of("ab", "cd", "ef"), chunks2);
    }

    @Test
    void generateSummarySerialisedRetriesThenSucceeds() throws Exception {
        EnvironmentVariables.LLM_MAX_CHARACTERS = 200000;
        Method m = DocumentSummarizerUtil.class.getDeclaredMethod("generateSummarySerialised", String.class, LLM.class, int.class, int.class);
        m.setAccessible(true);
        StubLLM llm = new StubLLM(1, "ok");
        String out = (String) m.invoke(null, "txt", llm, 10, 1000);
        assertEquals("ok", out);
    }

    @Test
    void generateSummarySerialisedThrowsAfterRetries() throws Exception {
        EnvironmentVariables.LLM_MAX_CHARACTERS = 200000;
        Method m = DocumentSummarizerUtil.class.getDeclaredMethod("generateSummarySerialised", String.class, LLM.class, int.class, int.class);
        m.setAccessible(true);
        StubLLM llm = new StubLLM(5, "never");
        InvocationTargetException ex = assertThrows(InvocationTargetException.class,
                () -> m.invoke(null, "txt", llm, 10, 1000));
        assertTrue(ex.getCause() instanceof HttpServerErrorException);
    }

    @Test
    void acquireLockInterruptedThrows() throws Exception {
        Thread.currentThread().interrupt();
        Method m = DocumentSummarizerUtil.class.getDeclaredMethod("generateSummarySerialised", String.class, LLM.class, int.class, int.class);
        m.setAccessible(true);
        InvocationTargetException ex = assertThrows(InvocationTargetException.class,
                () -> m.invoke(null, "txt", new StubLLM(0, "x"), 10, 1000));
        assertTrue(ex.getCause() instanceof RuntimeException);
        assertTrue(ex.getCause().getMessage().contains("Interrupted while waiting for LLM lock"));
    }

    @Test
    void charBudgetForBranches() throws Exception {
        Method m = DocumentSummarizerUtil.class.getDeclaredMethod("charBudgetFor", LLMModel.class);
        m.setAccessible(true);
        int az = (int) m.invoke(null, LLMModel.AZURE_OPENAI);
        assertEquals(150000, az);
        int other = (int) m.invoke(null, LLMModel.LOCAL_UZH);
        int expected = (int) Math.min(Math.round(LLMModel.LOCAL_UZH.getMaxTokens() * 4 * 0.85), Integer.MAX_VALUE);
        assertEquals(expected, other);
    }

    @Test
    void singleCallReturnsLLMResult() throws Exception {
        EnvironmentVariables.LLM_MAX_CHARACTERS = 200000;
        Method m = DocumentSummarizerUtil.class.getDeclaredMethod("singleCall", String.class, LLM.class, int.class);
        m.setAccessible(true);
        StubLLM llm = new StubLLM(0, "res");
        String out = (String) m.invoke(null, "text", llm, 100);
        assertEquals("res", out);
    }

    @Test
    void sleepHandlesInterrupted() throws Exception {
        Thread.currentThread().interrupt();
        Method m = DocumentSummarizerUtil.class.getDeclaredMethod("sleep", Duration.class);
        m.setAccessible(true);
        m.invoke(null, Duration.ofMillis(1));
        assertTrue(Thread.currentThread().isInterrupted());
    }
}
