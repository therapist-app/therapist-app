package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateExerciseComponentDTO {
  private String id;
  private String description;
  private Integer orderNumber;
}
