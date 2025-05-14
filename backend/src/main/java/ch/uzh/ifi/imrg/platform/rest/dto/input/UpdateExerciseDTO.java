package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import lombok.Data;

@Data
public class UpdateExerciseDTO {
  private String id;
  private String title;
  private ExerciseType exerciseType;
}
