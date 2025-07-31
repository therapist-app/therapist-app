package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateTherapistDTO {
  private String email;
  private String password;
}
