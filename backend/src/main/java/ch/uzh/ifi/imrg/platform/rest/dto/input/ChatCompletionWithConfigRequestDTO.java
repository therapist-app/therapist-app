package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class ChatCompletionWithConfigRequestDTO {
  private ChatbotConfigDTO config;
  private String message;
}
