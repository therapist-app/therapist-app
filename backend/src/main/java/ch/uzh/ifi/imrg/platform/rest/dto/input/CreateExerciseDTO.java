package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import java.time.Instant;
import lombok.Data;

@Data
public class CreateExerciseDTO {
  private String patientId;
  private String title;
  private ExerciseType exerciseType;
  private Instant exerciseStart;
  private long durationInWeeks;
}
