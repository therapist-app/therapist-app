package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class LoginTherapistDTO {
  private String email;
  private String password;
}
