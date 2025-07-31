package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateExerciseComponentDTO {
  private String id;
  private String exerciseComponentDescription;
  private String youtubeUrl;

  @Positive(message = "The order number must be a positive number.")
  private Integer orderNumber;
}
