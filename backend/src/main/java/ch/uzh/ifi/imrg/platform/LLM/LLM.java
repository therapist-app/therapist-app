package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LLM {

  private static final Logger logger = LoggerFactory.getLogger(LLM.class);

  protected static final String ANSI_RESET = "\u001B[0m";
  protected static final String ANSI_BLUE = "\u001B[34m"; // User
  protected static final String ANSI_YELLOW = "\u001B[33m"; // System
  protected static final String ANSI_CYAN = "\u001B[36m"; // Assistant
  protected static final String ANSI_GREEN = "\u001B[32m"; // LLM Response

  protected static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());

  public final <T> T callLLMForObject(
      List<ChatMessageDTO> messages, Class<T> responseType, Language language) {
    String rawContent = callLLM(messages, language);
    try {
      return objectMapper.readValue(rawContent, responseType);
    } catch (JsonProcessingException e) {
      try {
        String repairedContent = repairIncompleteJson(rawContent);
        return objectMapper.readValue(repairedContent, responseType);
      } catch (JsonProcessingException e2) {
        throw new RuntimeException(
            "Failed to parse LLM JSON response for type "
                + responseType.getSimpleName()
                + ". Raw content: "
                + rawContent,
            e2);
      }
    }
  }

  private String repairIncompleteJson(String rawContent) {
    String trimmed = rawContent.trim();

    // If it's empty or already looks like a complete object, do nothing.
    if (trimmed.isEmpty() || (trimmed.startsWith("{") && trimmed.endsWith("}"))) {
      return trimmed;
    }

    // Most common LLM failure: truncated before the end.
    if (trimmed.startsWith("{")) {
      logger.warn("Attempting to repair incomplete JSON response...");

      // 1. Fix unclosed string values.
      // Count non-escaped quotation marks. If odd, a string is open.
      long quoteCount = trimmed.chars().filter(ch -> ch == '"').count();
      if (quoteCount % 2 != 0) {
        trimmed += "\"";
        logger.warn("Added missing closing quote.");
      }

      // 2. Fix unclosed objects and arrays.
      int openBraces = 0;
      int closeBraces = 0;
      for (char c : trimmed.toCharArray()) {
        if (c == '{') openBraces++;
        if (c == '}') closeBraces++;
      }

      // Append missing closing braces
      if (openBraces > closeBraces) {
        for (int i = 0; i < (openBraces - closeBraces); i++) {
          trimmed += "}";
        }
        logger.warn("Added {} missing closing brace(s).", (openBraces - closeBraces));
      }

      logger.info("Repaired JSON: " + trimmed);
    }

    return trimmed;
  }

  public final String callLLM(List<ChatMessageDTO> messages, Language language) {
    if (language == null) {
      language = Language.English;
    }
    List<ChatMessageDTO> processedMessages = new ArrayList<>(messages);

    processedMessages = truncateMessages(processedMessages);

    enhanceSystemMessage(processedMessages, language);

    logRequestContext(processedMessages);

    String rawResponse = getRawLLMResponse(processedMessages, language);

    String filteredResponse = filterResponseContent(rawResponse);
    logger.info(
        ANSI_GREEN
            + "\n"
            + this.getClass().getSimpleName()
            + " Response:\n"
            + filteredResponse
            + ANSI_RESET);

    return filteredResponse;
  }

  protected abstract String getRawLLMResponse(List<ChatMessageDTO> messages, Language language);

  protected String filterResponseContent(String rawContent) {
    String filtered = rawContent.replaceAll("(?s)<think>.*?</think>", "").trim();
    if (filtered.startsWith("```json")) {
      filtered = filtered.substring(7, filtered.length() - 3).trim();
    }
    return filtered;
  }

  protected List<ChatMessageDTO> truncateMessages(List<ChatMessageDTO> allMessages) {
    int allCharacters = allMessages.stream().mapToInt(m -> m.getContent().length()).sum();

    if (allCharacters < EnvironmentVariables.LLM_MAX_CHARACTERS) {
      return allMessages;
    }
    logger.warn(
        "Message list exceeds max characters ({}), truncating...",
        EnvironmentVariables.LLM_MAX_CHARACTERS);

    final double LAST_MESSAGE_PERCENTAGE = 0.4;
    final double FIRST_MESSAGE_PERCENTAGE = 0.8;
    int remainingCharacters = EnvironmentVariables.LLM_MAX_CHARACTERS;

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

  private String truncateString(String message, double length) {
    if (length <= 0) return "";
    return message.length() > (int) length ? message.substring(0, (int) length) : message;
  }

  protected void enhanceSystemMessage(List<ChatMessageDTO> messages, Language language) {
    messages.stream()
        .filter(m -> m.getChatRole() == ChatRole.SYSTEM)
        .findFirst()
        .ifPresent(
            systemMessage -> {
              StringBuilder sb = new StringBuilder();
              sb.append("The current Date and Time is: " + FormatUtil.formatDate(Instant.now()));
              sb.append(
                  "\n\nCRITICAL: Your entire response MUST be in "
                      + language
                      + " (even if you cannot answer).\n\n");
              sb.append(systemMessage.getContent());
              sb.append(
                  "\n\nCRITICAL: Your entire response MUST be in "
                      + language
                      + " (even if you cannot answer).");

              systemMessage.setContent(sb.toString());
            });
  }

  protected void logRequestContext(List<ChatMessageDTO> messages) {
    StringBuilder contextLog =
        new StringBuilder("\n--- LLM Request Context (")
            .append(this.getClass().getSimpleName())
            .append(") ---");
    for (ChatMessageDTO msg : messages) {
      String role = msg.getChatRole().toString().toLowerCase();
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
  }
}
