package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTherapySessionDTO {

  private LocalDateTime sessionStart;

  private LocalDateTime sessionEnd;

  private String patientId;
}
