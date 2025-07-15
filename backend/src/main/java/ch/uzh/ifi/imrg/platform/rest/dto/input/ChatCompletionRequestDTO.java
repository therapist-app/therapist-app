package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import java.util.List;
import lombok.Data;

@Data
public class ChatCompletionRequestDTO {
  private List<ChatMessageDTO> messages;
  private Language language;
}
