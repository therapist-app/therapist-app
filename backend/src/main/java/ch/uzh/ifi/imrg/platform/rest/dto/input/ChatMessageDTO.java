package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageDTO {
  private String role;
  private String content;
}
