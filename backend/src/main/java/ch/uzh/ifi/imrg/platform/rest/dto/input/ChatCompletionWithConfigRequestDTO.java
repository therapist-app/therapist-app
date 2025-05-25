package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.util.List;
import lombok.Data;

@Data
public class ChatCompletionWithConfigRequestDTO {
  private ChatbotConfigDTO config;
  private List<ChatMessageDTO> history;
  private String message;
}