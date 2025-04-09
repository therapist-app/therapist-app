package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TherapySessionOutputDTO {

  private String id;
  private LocalDateTime sessionStart;
  private LocalDateTime sessionEnd;
}
