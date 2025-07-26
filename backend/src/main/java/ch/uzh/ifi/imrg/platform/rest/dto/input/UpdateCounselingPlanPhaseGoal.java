package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateCounselingPlanPhaseGoal {
  private String counselingPlanPhaseGoalId;
  private String goalName;
  private String goalDescription;
}
