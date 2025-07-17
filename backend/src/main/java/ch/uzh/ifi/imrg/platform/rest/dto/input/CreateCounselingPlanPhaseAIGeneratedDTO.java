package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import lombok.Data;

@Data
public class CreateCounselingPlanPhaseAIGeneratedDTO {
  private String counselingPlanId;
  private Language language;
}
