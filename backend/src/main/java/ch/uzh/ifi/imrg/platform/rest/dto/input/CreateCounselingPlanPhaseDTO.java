package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCounselingPlanPhaseDTO {
  private String counselingPlanId;
  private String phaseName;

  @Positive(message = "Duration in weeks must be a positive number.")
  private Integer durationInWeeks;
}
