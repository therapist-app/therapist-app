package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class CounselingPlanOutputDTO {
  private String id;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant startOfTherapy;
  private List<CounselingPlanPhaseOutputDTO> counselingPlanPhasesOutputDTO;
}
