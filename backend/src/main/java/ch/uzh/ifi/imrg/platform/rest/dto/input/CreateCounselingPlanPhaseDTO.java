package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.Instant;
import lombok.Data;

@Data
public class CreateCounselingPlanPhaseDTO {
  private String counselingPlanId;
  private String phaseName;
  private Instant startDate;
  private long durationInWeeks;
}
