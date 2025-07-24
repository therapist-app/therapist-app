package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Data
class RemoteResponse {
  private List<Choice> choices;

  @Data
  static class Choice {
    private Message message;
  }

  @Data
  static class Message {
    private String role;
    private String content;
  }
}

@Data
class RequestPayload {
  private String model = EnvironmentVariables.LOCAL_LLM_MODEL;
  private List<Message> messages;

  @Data
  static class Message {
    private String role;
    private String content;
  }
}

public class LLMUZH extends LLM {

  @Override
  protected String getRawLLMResponse(List<ChatMessageDTO> messages, Language language) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(EnvironmentVariables.LOCAL_LLM_API_KEY);
    headers.setContentType(MediaType.APPLICATION_JSON);

    RequestPayload payload = new RequestPayload();
    payload.setMessages(
        messages.stream().map(this::mapToRequestMessage).collect(Collectors.toList()));

    HttpEntity<RequestPayload> entity = new HttpEntity<>(payload, headers);

    ResponseEntity<RemoteResponse> response =
        new RestTemplate()
            .exchange(
                EnvironmentVariables.LOCAL_LLM_URL, HttpMethod.POST, entity, RemoteResponse.class);

    if (response.getBody() != null
        && response.getBody().getChoices() != null
        && !response.getBody().getChoices().isEmpty()) {
      return response.getBody().getChoices().get(0).getMessage().getContent();
    }

    throw new RuntimeException("Did not receive a valid message from the LLM UZH API.");
  }

  private RequestPayload.Message mapToRequestMessage(ChatMessageDTO dto) {
    RequestPayload.Message msg = new RequestPayload.Message();
    msg.setRole(mapChatRoleToString(dto.getChatRole()));
    msg.setContent(dto.getContent());
    return msg;
  }

  private String mapChatRoleToString(ChatRole chatRole) {
    return chatRole.toString().toLowerCase(); // e.g., USER -> "user"
  }
}
