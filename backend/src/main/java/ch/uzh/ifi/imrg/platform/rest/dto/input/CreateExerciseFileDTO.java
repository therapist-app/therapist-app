package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateExerciseFileDTO {
  private String exerciseId;
  private String description;
}
