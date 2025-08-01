package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PsychologicalTestCreateDTO {
  private String patientId;
  private String testName;
  private Instant exerciseStart;
  private Instant exerciseEnd;
  private Boolean isPaused;
  private Integer doEveryNDays;
}
