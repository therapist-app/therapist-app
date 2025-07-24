package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LLMAuzureOpenai extends LLM {

  private static final Logger logger = LoggerFactory.getLogger(LLMAuzureOpenai.class);

  // ANSI codes for colored logging
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLUE = "\u001B[34m"; // User
  public static final String ANSI_YELLOW = "\u001B[33m"; // System
  public static final String ANSI_CYAN = "\u001B[36m"; // Assistant
  public static final String ANSI_GREEN = "\u001B[32m"; // LLM Response

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());

  private static final OpenAIClient client =
      new OpenAIClientBuilder()
          .endpoint(EnvironmentVariables.AZURE_OPENAI_ENDPOINT)
          .credential(new AzureKeyCredential(EnvironmentVariables.AZURE_OPENAI_API_KEY))
          .buildClient();

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

    List<ChatMessageDTO> truncatedMessages = truncateMessages(new ArrayList<>(messages));

    List<ChatRequestMessage> sdkMessages = new ArrayList<>();
    for (ChatMessageDTO msg : truncatedMessages) {
      if (msg.getChatRole() == ChatRole.SYSTEM) {
        String enhancedContent = enhanceSystemMessage(msg.getContent(), language);
        msg.setContent(enhancedContent);
      }
      sdkMessages.add(mapToSdkMessage(msg));
    }

    logRequestContext(sdkMessages);

    ChatCompletionsOptions options = new ChatCompletionsOptions(sdkMessages).setTemperature(0.1);

    ChatCompletions chatCompletions =
        client.getChatCompletions(EnvironmentVariables.AZURE_OPENAI_DEPLOYMENT_NAME, options);

    if (chatCompletions != null && !chatCompletions.getChoices().isEmpty()) {
      ChatChoice choice = chatCompletions.getChoices().get(0);
      String rawContent = choice.getMessage().getContent();
      String filteredContent = rawContent.replaceAll("(?s)<think>.*?</think>", "").trim();

      if (filteredContent.startsWith("```json")) {
        filteredContent = filteredContent.substring(7, filteredContent.length() - 3).trim();
      }

      logger.info(ANSI_GREEN + "\nAzure LLM Response:\n" + filteredContent + ANSI_RESET);
      return filteredContent;
    }

    throw new RuntimeException("Did not receive a valid message from the Azure OpenAI API.");
  }

  private static ChatRequestMessage mapToSdkMessage(ChatMessageDTO dto) {
    switch (dto.getChatRole()) {
      case USER:
        return new ChatRequestUserMessage(dto.getContent());
      case ASSISTANT:
        return new ChatRequestAssistantMessage(dto.getContent());
      case SYSTEM:
        return new ChatRequestSystemMessage(dto.getContent());
      default:
        throw new IllegalArgumentException("Invalid chat role: " + dto.getChatRole());
    }
  }

  private static String enhanceSystemMessage(String originalContent, Language language) {
    return String.format(
        "The current Date and Time is: %s\n\nCRITICAL: Your entire response MUST be in %s (even if you cannot answer).\n\n%s\n\nCRITICAL: Your entire response MUST be in %s (even if you cannot answer).",
        FormatUtil.formatDate(Instant.now()), language, originalContent, language);
  }

  private static void logRequestContext(List<ChatRequestMessage> messages) {
    StringBuilder contextLog = new StringBuilder("\n--- Azure LLM Request Context (SDK) ---");
    for (ChatRequestMessage msg : messages) {
      String role = msg.getRole().toString().toLowerCase();
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
          .append(msg.toString());
    }
    logger.info(contextLog.toString());
  }

  private static List<ChatMessageDTO> truncateMessages(List<ChatMessageDTO> allMessages) {
    final int MAX_CHARACTERS = 150000;
    int allCharacters = allMessages.stream().mapToInt(m -> m.getContent().length()).sum();

    if (allCharacters < MAX_CHARACTERS) {
      return allMessages;
    }

    logger.warn("Message list exceeds max characters, truncating...");

    final double LAST_MESSAGE_PERCENTAGE = 0.4;
    final double FIRST_MESSAGE_PERCENTAGE = 0.8;
    int remainingCharacters = MAX_CHARACTERS;

    ChatMessageDTO lastMessage = allMessages.remove(allMessages.size() - 1);
    lastMessage.setContent(
        truncateString(lastMessage.getContent(), remainingCharacters * LAST_MESSAGE_PERCENTAGE));
    remainingCharacters -= lastMessage.getContent().length();

    ChatMessageDTO firstMessage = allMessages.remove(0);
    firstMessage.setContent(
        truncateString(firstMessage.getContent(), remainingCharacters * FIRST_MESSAGE_PERCENTAGE));
    remainingCharacters -= firstMessage.getContent().length();

    List<ChatMessageDTO> finalMessages = new ArrayList<>();
    finalMessages.add(firstMessage);

    for (ChatMessageDTO currentMessage : allMessages) {
      if (remainingCharacters <= 100) break;
      currentMessage.setContent(truncateString(currentMessage.getContent(), remainingCharacters));
      remainingCharacters -= currentMessage.getContent().length();
      finalMessages.add(currentMessage);
    }

    finalMessages.add(lastMessage);
    return finalMessages;
  }

  private static String truncateString(String message, double length) {
    if (length <= 0) return "";
    return message.length() > (int) length ? message.substring(0, (int) length) : message;
  }
}
