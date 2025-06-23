package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateCounselingPlanPhaseGoalDTO {
  private String counselingPlanPhaseId;
  private String goalName;
  private String goalDescription;
}
