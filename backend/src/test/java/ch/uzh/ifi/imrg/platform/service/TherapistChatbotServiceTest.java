package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.LLM.LLM;
import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.TherapistChatbotInputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatbotOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

class TherapistChatbotServiceTest {

    @Test
    void chat_withoutPatient_returnsLlmResponse() {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        PatientAppContextService ctxSvc = mock(PatientAppContextService.class);

        Therapist t = mock(Therapist.class);
        when(tRepo.getReferenceById("T1")).thenReturn(t);

        LLM llm = mock(LLM.class);
        when(llm.callLLM(anyList(), isNull())).thenReturn("RESP");

        try (MockedStatic<LLMFactory> ms = mockStatic(LLMFactory.class)) {
            ms.when(() -> LLMFactory.getInstance(null)).thenReturn(llm);

            TherapistChatbotService svc = new TherapistChatbotService(pRepo, tRepo, ctxSvc);

            TherapistChatbotInputDTO in = new TherapistChatbotInputDTO();
            in.setChatMessages(List.of(new ChatMessageDTO(ChatRole.USER, "hi")));

            TherapistChatbotOutputDTO out = svc.chat(in, "T1");
            assertEquals("RESP", out.getContent());

            verify(llm).callLLM(anyList(), isNull());
        }
    }

    @Test
    void chat_withPatient_usesPatientContextAndReturnsResponse() {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        PatientAppContextService ctxSvc = mock(PatientAppContextService.class);

        Therapist t = mock(Therapist.class);
        when(tRepo.getReferenceById("T2")).thenReturn(t);

        Patient p = mock(Patient.class);
        when(p.getId()).thenReturn("P2");
        when(pRepo.getReferenceById("P2")).thenReturn(p);
        when(ctxSvc.buildContext("P2")).thenReturn("CTX");

        LLM llm = mock(LLM.class);
        when(llm.callLLM(anyList(), isNull())).thenReturn("OK");

        try (MockedStatic<LLMFactory> ms = mockStatic(LLMFactory.class)) {
            ms.when(() -> LLMFactory.getInstance(null)).thenReturn(llm);

            TherapistChatbotService svc = new TherapistChatbotService(pRepo, tRepo, ctxSvc);

            TherapistChatbotInputDTO in = new TherapistChatbotInputDTO();
            in.setPatientId("P2");
            in.setChatMessages(List.of());

            TherapistChatbotOutputDTO out = svc.chat(in, "T2");
            assertEquals("OK", out.getContent());

            verify(llm).callLLM(anyList(), isNull());
            verify(ctxSvc).buildContext("P2");
        }
    }
}
