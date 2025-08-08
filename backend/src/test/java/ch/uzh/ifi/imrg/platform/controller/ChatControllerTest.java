package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatCompletionWithConfigRequestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatCompletionResponseDTO;
import ch.uzh.ifi.imrg.platform.service.ChatMessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class ChatControllerTest {

    @Test
    @DisplayName("chatWithConfig() forwards request & therapistId to service")
    void forwardsToService() {
        ChatMessageService service = Mockito.mock(ChatMessageService.class);
        ChatController controller = new ChatController(service);

        ChatCompletionWithConfigRequestDTO req =
                Mockito.mock(ChatCompletionWithConfigRequestDTO.class);
        ChatCompletionResponseDTO expected = new ChatCompletionResponseDTO("ok");
        Mockito.when(service.chat(req, "t-1")).thenReturn(expected);

        ChatCompletionResponseDTO actual = controller.chatWithConfig(req, "t-1");

        assertThat(actual).isSameAs(expected);
        Mockito.verify(service).chat(req, "t-1");
    }
}
