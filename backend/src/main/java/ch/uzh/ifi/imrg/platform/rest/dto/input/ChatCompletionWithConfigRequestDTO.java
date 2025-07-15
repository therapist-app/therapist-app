package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import java.util.List;
import lombok.Data;

@Data
public class ChatCompletionWithConfigRequestDTO {
  private String templateId;
  private ChatbotConfigDTO config;
  private List<ChatMessageDTO> history;
  private String message;
  private Language language;
}
