package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateCounselingPlanPhaseGoalDTO {
  private String counselingPlanPhaseGoalId;
  private String goalName;
  private String goalDescription;
}
