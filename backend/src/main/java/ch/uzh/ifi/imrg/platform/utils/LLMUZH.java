package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

public class LLMUZH implements LLM {

  public static String callLLM(List<ChatMessageDTO> messages) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(EnvironmentVariables.LOCAL_LLM_API_KEY);
    headers.setContentType(MediaType.APPLICATION_JSON);

    List<RequestPayload.Message> requestMessages = new ArrayList<>();
    for (ChatMessageDTO message : messages) {
      RequestPayload.Message requestMessage = new RequestPayload.Message();
      requestMessage.setRole(mapChatRolesToRequestRoles(message.getChatRole()));
      requestMessage.setContent(message.getContent());
      requestMessages.add(requestMessage);
    }

    RequestPayload payload = new RequestPayload();
    payload.setMessages(requestMessages);

    ResponseEntity<RemoteResponse> response =
        new RestTemplate()
            .exchange(
                EnvironmentVariables.LOCAL_LLM_URL,
                HttpMethod.POST,
                new HttpEntity<>(payload, headers),
                RemoteResponse.class);

    if (response.getBody() != null
        && response.getBody().getChoices() != null
        && !response.getBody().getChoices().isEmpty()) {
      RemoteResponse.Choice choice = response.getBody().getChoices().get(0);
      return choice.getMessage().getContent();
    }

    throw new Error("Did not find message");
  }

  private static String mapChatRolesToRequestRoles(ChatRole chatRole) {
    switch (chatRole) {
      case USER:
        return "user";
      case ASSISTANT:
        return "assistant";
      case SYSTEM:
        return "system";
      default:
        throw new IllegalArgumentException("Invalid chat role: " + chatRole);
    }
  }
}
