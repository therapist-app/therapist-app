package ch.uzh.ifi.imrg.platform.rest.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatCompletionResponseDTO {
  private String content;
}
