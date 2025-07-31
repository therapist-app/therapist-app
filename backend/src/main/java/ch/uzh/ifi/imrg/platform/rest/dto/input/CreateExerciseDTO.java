package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.constraints.Positive;
import java.time.Instant;
import lombok.Data;

@Data
public class CreateExerciseDTO {
  private String patientId;
  private String exerciseTitle;
  private String exerciseDescription;
  private String exerciseExplanation;
  private Instant exerciseStart;

  @Positive(message = "Duration must be a positive number.")
  private Integer durationInWeeks;

  @Positive(message = "Do every ... days must be a positive number.")
  private Integer doEveryNDays;
}
