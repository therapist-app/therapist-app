package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import lombok.Data;

@Data
public class UpdateTherapistDTO {
  private String password;
  private Language language;
}
