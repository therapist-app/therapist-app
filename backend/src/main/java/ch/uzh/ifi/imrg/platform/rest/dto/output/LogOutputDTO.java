package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutputDTO {
  private String id;
  private String patientId;
  private String logType;
  private Instant timestamp;
  private String uniqueIdentifier;
  private String comment;
}
