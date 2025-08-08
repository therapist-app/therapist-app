package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.LLM.LLM;
import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatCompletionWithConfigRequestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatbotConfigDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatCompletionResponseDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ChatMessageServiceTest {

    @Test
    void chat_withConfigDocsAndNoHistory() {
        ChatbotTemplateRepository tplRepo = mock(ChatbotTemplateRepository.class);
        TherapistRepository thRepo = mock(TherapistRepository.class);
        ChatMessageService svc = new ChatMessageService(tplRepo, thRepo);

        ChatbotTemplate tpl = mock(ChatbotTemplate.class);
        ChatbotTemplateDocument d1 = mock(ChatbotTemplateDocument.class);
        when(d1.getFileName()).thenReturn(null);
        when(d1.getExtractedText()).thenReturn("  DOC1  ");
        ChatbotTemplateDocument d2 = mock(ChatbotTemplateDocument.class);
        when(d2.getFileName()).thenReturn("X");
        when(d2.getExtractedText()).thenReturn("");
        ChatbotTemplateDocument d3 = mock(ChatbotTemplateDocument.class);
        when(d3.getExtractedText()).thenReturn(null);
        when(tpl.getChatbotTemplateDocuments()).thenReturn(List.of(d1, d2, d3));
        when(tplRepo.findByIdAndTherapistId("T1", "TH")).thenReturn(Optional.of(tpl));

        Therapist therapist = mock(Therapist.class);
        when(thRepo.getReferenceById("TH")).thenReturn(therapist);

        LLM llm = mock(LLM.class);
        when(llm.callLLM(anyList(), isNull())).thenReturn("R1");
        try (MockedStatic<LLMFactory> ms = mockStatic(LLMFactory.class)) {
            ms.when(() -> LLMFactory.getInstance(null)).thenReturn(llm);

            ChatbotConfigDTO cfg = new ChatbotConfigDTO();
            cfg.setChatbotRole("coach");
            cfg.setChatbotTone("warm");
            cfg.setWelcomeMessage("hello");

            ChatCompletionWithConfigRequestDTO req = new ChatCompletionWithConfigRequestDTO();
            req.setTemplateId("T1");
            req.setConfig(cfg);
            req.setMessage("Hi");

            ChatCompletionResponseDTO out = svc.chat(req, "TH");
            assertEquals("R1", out.getContent());
            verify(llm).callLLM(argThat(list ->
                            !list.isEmpty()
                                    && ((ChatMessageDTO) list.get(0)).getChatRole() == ChatRole.SYSTEM),
                    isNull());
        }
    }

    @Test
    void chat_emptyConfigNoDocsWithHistory() {
        ChatbotTemplateRepository tplRepo = mock(ChatbotTemplateRepository.class);
        TherapistRepository thRepo = mock(TherapistRepository.class);
        ChatMessageService svc = new ChatMessageService(tplRepo, thRepo);

        ChatbotTemplate tpl = mock(ChatbotTemplate.class);
        when(tpl.getChatbotTemplateDocuments()).thenReturn(List.of());
        when(tplRepo.findByIdAndTherapistId("T2", "TH2")).thenReturn(Optional.of(tpl));

        Therapist therapist = mock(Therapist.class);
        when(thRepo.getReferenceById("TH2")).thenReturn(therapist);

        LLM llm = mock(LLM.class);
        when(llm.callLLM(anyList(), isNull())).thenReturn("R2");
        try (MockedStatic<LLMFactory> ms = mockStatic(LLMFactory.class)) {
            ms.when(() -> LLMFactory.getInstance(null)).thenReturn(llm);

            ChatbotConfigDTO cfg = new ChatbotConfigDTO();
            cfg.setChatbotRole(" ");
            cfg.setChatbotTone(null);
            cfg.setWelcomeMessage("");

            ChatCompletionWithConfigRequestDTO req = new ChatCompletionWithConfigRequestDTO();
            req.setTemplateId("T2");
            req.setConfig(cfg);
            req.setHistory(List.of(new ChatMessageDTO(ChatRole.USER, "prev")));
            req.setMessage("Hi2");

            ChatCompletionResponseDTO out = svc.chat(req, "TH2");
            assertEquals("R2", out.getContent());
            verify(llm).callLLM(argThat(list -> list.size() >= 2), isNull());
        }
    }

    @Test
    void chat_templateNotFound() {
        ChatbotTemplateRepository tplRepo = mock(ChatbotTemplateRepository.class);
        TherapistRepository thRepo = mock(TherapistRepository.class);
        ChatMessageService svc = new ChatMessageService(tplRepo, thRepo);

        when(tplRepo.findByIdAndTherapistId("NA", "X")).thenReturn(Optional.empty());

        ChatCompletionWithConfigRequestDTO req = new ChatCompletionWithConfigRequestDTO();
        req.setTemplateId("NA");
        req.setConfig(new ChatbotConfigDTO());
        req.setMessage("m");

        assertThrows(EntityNotFoundException.class, () -> svc.chat(req, "X"));
    }
}
