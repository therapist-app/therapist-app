package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ExerciseOutputDTO {
  private String id;
  private String title;
  private ExerciseType exerciseType;
  private LocalDateTime exerciseStart;
  private LocalDateTime exerciseEnd;
  private Boolean isPaused;
  private List<ExerciseComponentOutputDTO> exerciseComponentsOutputDTO;
}
