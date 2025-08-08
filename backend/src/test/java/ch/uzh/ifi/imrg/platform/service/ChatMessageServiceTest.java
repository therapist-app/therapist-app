package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatCompletionWithConfigRequestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatbotConfigDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ChatMessageServiceTest {

    private interface DummyLLM {
        String callLLM(List<?> messages, Object language);
    }

    private static final String TH_ID  = "thera-42";
    private static final String TPL_ID = "tpl-99";

    private final ChatbotTemplateRepository tplRepo   = Mockito.mock(ChatbotTemplateRepository.class);
    private final TherapistRepository       thRepo    = Mockito.mock(TherapistRepository.class);
    private final ChatMessageService        service   = new ChatMessageService(tplRepo, thRepo);

    private ChatCompletionWithConfigRequestDTO minimalRequest() {
        ChatCompletionWithConfigRequestDTO req = Mockito.mock(ChatCompletionWithConfigRequestDTO.class, Mockito.RETURNS_DEFAULTS);
        Mockito.when(req.getTemplateId()).thenReturn(TPL_ID);
        Mockito.when(req.getMessage()).thenReturn("Hi!");
        Mockito.when(req.getHistory()).thenReturn(null);
        Mockito.when(req.getLanguage()).thenReturn(null);
        Mockito.when(req.getConfig()).thenReturn(new ChatbotConfigDTO());
        return req;
    }

    @Test
    @DisplayName("unknown template id – throws EntityNotFoundException")
    void templateNotFound() {
        Mockito.when(tplRepo.findByIdAndTherapistId(TPL_ID, TH_ID))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.chat(minimalRequest(), TH_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("template");
    }
}
