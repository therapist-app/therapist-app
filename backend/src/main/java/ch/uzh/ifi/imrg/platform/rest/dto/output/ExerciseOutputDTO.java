package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class ExerciseOutputDTO {
  private String id;
  private String title;
  private ExerciseType exerciseType;
  private Instant exerciseStart;
  private Instant exerciseEnd;
  private Boolean isPaused;
  private List<ExerciseComponentOutputDTO> exerciseComponentsOutputDTO;
}
