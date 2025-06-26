package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class RemoveExerciseFromCounselingPlanPhaseDTO {
  private String exerciseId;
  private String counselingPlanPhaseId;
}
