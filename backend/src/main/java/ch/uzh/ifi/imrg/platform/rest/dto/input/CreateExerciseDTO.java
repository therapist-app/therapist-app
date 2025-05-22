package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import lombok.Data;

@Data
public class CreateExerciseDTO {
  private String title;
  private ExerciseType exerciseType;
}
