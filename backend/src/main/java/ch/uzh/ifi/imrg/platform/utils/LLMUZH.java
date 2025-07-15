package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger logger = LoggerFactory.getLogger(LLMUZH.class);

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLUE = "\u001B[34m"; // User
  public static final String ANSI_YELLOW = "\u001B[33m"; // System
  public static final String ANSI_CYAN = "\u001B[36m"; // Assistant
  public static final String ANSI_GREEN = "\u001B[32m"; // LLM Response

  private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  public static <T> T callLLMForObject(
      List<ChatMessageDTO> messages, Class<T> responseType, Language language) {
    String rawContent = getLLMResponseContent(messages, language);
    try {
      return objectMapper.readValue(rawContent, responseType);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(
          "Failed to parse LLM JSON response for type "
              + responseType.getSimpleName()
              + ". Raw content: "
              + rawContent,
          e);
    }
  }

  public static String callLLM(List<ChatMessageDTO> messages, Language language) {
    return getLLMResponseContent(messages, language);
  }

  private static String getLLMResponseContent(List<ChatMessageDTO> messages, Language language) {
    if (language == null) {
      language = Language.English;
    }
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

    List<RequestPayload.Message> finalMessages = truncateMessages(requestMessages);

    RequestPayload.Message systemMessage = finalMessages.stream()
        .filter(m -> "system".equals(m.getRole()))
        .findFirst()
        .orElse(null);
    String newContent = "CRITICAL: Your entire response MUST be in "
        + language
        + " (even if you cannot answer).\n\n"
        + systemMessage.getContent()
        + "\n\nCRITICAL: Your entire response MUST be in "
        + language
        + " (even if you cannot answer).";
    if (systemMessage != null) {
      systemMessage.setContent(newContent);
    }

    RequestPayload payload = new RequestPayload();
    payload.setMessages(finalMessages);

    StringBuilder contextLog = new StringBuilder("\n--- LLM Request Context ---");
    for (RequestPayload.Message msg : payload.getMessages()) {
      String role = msg.getRole();
      String color;
      switch (role) {
        case "user":
          color = ANSI_BLUE;
          break;
        case "system":
          color = ANSI_YELLOW;
          break;
        case "assistant":
          color = ANSI_CYAN;
          break;
        default:
          color = ANSI_RESET;
          break;
      }

      String formattedRole = Character.toUpperCase(role.charAt(0)) + role.substring(1);

      contextLog
          .append("\n")
          .append(color)
          .append("--- ")
          .append(formattedRole)
          .append(" Message ---")
          .append(ANSI_RESET)
          .append("\n")
          .append(msg.getContent());
    }
    logger.info(contextLog.toString());

    ResponseEntity<RemoteResponse> response = new RestTemplate()
        .exchange(
            EnvironmentVariables.LOCAL_LLM_URL,
            HttpMethod.POST,
            new HttpEntity<>(payload, headers),
            RemoteResponse.class);

    if (response.getBody() != null
        && response.getBody().getChoices() != null
        && !response.getBody().getChoices().isEmpty()) {
      RemoteResponse.Choice choice = response.getBody().getChoices().get(0);
      String rawContent = choice.getMessage().getContent();
      String filteredContent = rawContent.replaceAll("(?s)<think>.*?</think>", "").trim();

      if (filteredContent.startsWith("```json")) {
        filteredContent = filteredContent.substring(7, filteredContent.length() - 3).trim();
      }

      logger.info(ANSI_GREEN + "\nLLM Response:\n" + filteredContent + ANSI_RESET);
      return filteredContent;
    }

    throw new Error("Did not receive a valid message from the LLM API.");
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

  private static List<RequestPayload.Message> truncateMessages(List<RequestPayload.Message> allMessages) {

    final int MAX_CHARACTERS = 150000;
    final double LAST_MESSAGE_PERCENTAGE = 0.4;
    final double FIRST_MESSAGE_PERCENTAGE = 0.8;

    int allCharacters = 0;
    for (RequestPayload.Message message : allMessages) {
      allCharacters += message.getContent().length();
    }

    if (allCharacters < MAX_CHARACTERS) {
      return allMessages;
    }

    int remainingCharacters = MAX_CHARACTERS;

    RequestPayload.Message lastMessage = allMessages.getLast();
    allMessages.remove(lastMessage);

    lastMessage.setContent(truncateMessage(lastMessage.getContent(), remainingCharacters * LAST_MESSAGE_PERCENTAGE));
    remainingCharacters -= lastMessage.getContent().length();

    RequestPayload.Message firstMessage = allMessages.get(0);
    allMessages.remove(firstMessage);

    firstMessage.setContent(truncateMessage(firstMessage.getContent(), remainingCharacters * FIRST_MESSAGE_PERCENTAGE));
    remainingCharacters -= firstMessage.getContent().length();

    List<RequestPayload.Message> finalMessages = new ArrayList<>();
    finalMessages.add(firstMessage);

    for (RequestPayload.Message currentMessage : allMessages) {
      if (remainingCharacters <= 100) {
        break;
      }
      currentMessage.setContent(truncateMessage(currentMessage.getContent(), remainingCharacters));
      remainingCharacters -= currentMessage.getContent().length();
      finalMessages.add(currentMessage);
    }

    finalMessages.add(lastMessage);

    return finalMessages;
  }

  private static String truncateMessage(String message, double length) {
    if (length <= 0) {
      return "";
    }
    if (length >= message.length()) {
      return message;
    }
    return message.substring(0, (int) length);
  }

}
