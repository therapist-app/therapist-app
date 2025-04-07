package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TherapySessionOutputDTO {

  private String id;
  private LocalDateTime sessionStart;
  private LocalDateTime sessionEnd;

}
