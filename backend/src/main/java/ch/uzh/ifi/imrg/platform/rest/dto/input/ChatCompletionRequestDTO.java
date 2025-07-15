package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.util.List;

import ch.uzh.ifi.imrg.platform.enums.Language;
import lombok.Data;

@Data
public class ChatCompletionRequestDTO {
  private List<ChatMessageDTO> messages;
  private Language language;
}
