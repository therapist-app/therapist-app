package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTherapistDTO {

  private String email;
  private String password;
}
