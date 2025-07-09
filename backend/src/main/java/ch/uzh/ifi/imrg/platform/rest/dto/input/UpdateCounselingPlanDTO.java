package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.Instant;
import lombok.Data;

@Data
public class UpdateCounselingPlanDTO {
  private String counselingPlanId;
  private Instant startOfTherapy;
}
