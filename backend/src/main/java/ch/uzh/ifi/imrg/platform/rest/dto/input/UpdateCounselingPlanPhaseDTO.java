package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCounselingPlanPhaseDTO {
  private String counselingPlanPhaseId;
  private String phaseName;

  @Positive(message = "The duration in weeks must be a positive number.")
  private Integer durationInWeeks;
}
