package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpdateExerciseDTO {
  private String id;
  private String title;
  private ExerciseType exerciseType;
  private LocalDateTime exerciseStart;
  private LocalDateTime exerciseEnd;
  private Boolean isPaused;
}
