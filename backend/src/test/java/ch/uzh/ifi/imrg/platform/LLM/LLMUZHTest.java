package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LLMUZHTest {

    @Test
    void nullBodyThrows() {
        ResponseEntity<RemoteResponse> respEnt = new ResponseEntity<>(null, HttpStatus.OK);
        try (MockedConstruction<RestTemplate> mc = mockConstruction(
                RestTemplate.class,
                (rest, ctx) -> when(rest.exchange(anyString(), any(), any(), eq(RemoteResponse.class)))
                        .thenReturn(respEnt))) {

            LLMUZH llm = new LLMUZH();
            assertThrows(RuntimeException.class,
                    () -> llm.getRawLLMResponse(List.of(new ChatMessageDTO(ChatRole.USER, "x")), Language.German));
        }
    }

    @Test
    void nullChoicesThrows() {
        RemoteResponse rr = new RemoteResponse();
        rr.setChoices(null);
        ResponseEntity<RemoteResponse> respEnt = new ResponseEntity<>(rr, HttpStatus.OK);
        try (MockedConstruction<RestTemplate> mc = mockConstruction(
                RestTemplate.class,
                (rest, ctx) -> when(rest.exchange(anyString(), any(), any(), eq(RemoteResponse.class)))
                        .thenReturn(respEnt))) {

            LLMUZH llm = new LLMUZH();
            assertThrows(RuntimeException.class,
                    () -> llm.getRawLLMResponse(List.of(new ChatMessageDTO(ChatRole.USER, "x")), Language.German));
        }
    }

    @Test
    void emptyChoicesThrows() {
        RemoteResponse rr = new RemoteResponse();
        rr.setChoices(List.of());
        ResponseEntity<RemoteResponse> respEnt = new ResponseEntity<>(rr, HttpStatus.OK);
        try (MockedConstruction<RestTemplate> mc = mockConstruction(
                RestTemplate.class,
                (rest, ctx) -> when(rest.exchange(anyString(), any(), any(), eq(RemoteResponse.class)))
                        .thenReturn(respEnt))) {

            LLMUZH llm = new LLMUZH();
            assertThrows(RuntimeException.class,
                    () -> llm.getRawLLMResponse(List.of(new ChatMessageDTO(ChatRole.USER, "x")), Language.German));
        }
    }
}
