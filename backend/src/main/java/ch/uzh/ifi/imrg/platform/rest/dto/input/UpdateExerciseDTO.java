package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.constraints.Positive;
import java.time.Instant;
import lombok.Data;

@Data
public class UpdateExerciseDTO {
  private String id;
  private String exerciseTitle;
  private String exerciseDescription;
  private String exerciseExplanation;
  private Instant exerciseStart;
  private Instant exerciseEnd;
  private Boolean isPaused;

  @Positive(message = "Do every ... days must be a positive number.")
  private Integer doEveryNDays;
}
