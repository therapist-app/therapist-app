package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateExerciseTextDTO {
  private String exerciseId;
  private String text;
}
