package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import lombok.Data;

@Data
public class UpdateTherapistDTO {
  private LLMModel llmModel;
  private String password;
}
