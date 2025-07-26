package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateCounselingPlanPhase {
  private String counselingPlanPhaseId;
  private String phaseName;
  private Integer durationInWeeks;
}
