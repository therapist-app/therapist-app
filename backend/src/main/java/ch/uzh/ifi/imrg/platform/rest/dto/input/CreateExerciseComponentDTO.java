package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;
import lombok.Data;

@Data
public class CreateExerciseComponentDTO {
  private String exerciseId;
  private String description;
  private ExerciseComponentType exerciseComponentType;
}
