package ch.uzh.ifi.imrg.platform.rest.dto.output;

import lombok.Data;

@Data
public class CounselingPlanPhaseGoalOutputDTO {
  private String id;
  private String goalName;
  private String goalDescription;
}
