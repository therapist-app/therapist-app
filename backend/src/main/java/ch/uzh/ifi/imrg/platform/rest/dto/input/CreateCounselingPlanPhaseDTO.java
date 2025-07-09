package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateCounselingPlanPhaseDTO {
  private String counselingPlanId;
  private String phaseName;
  private int durationInWeeks;
}
