package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateCounselingPlanPhaseDTO {
  private String counselingPlanPhaseId;
  private String phaseName;
  private Integer durationInWeeks;
}
