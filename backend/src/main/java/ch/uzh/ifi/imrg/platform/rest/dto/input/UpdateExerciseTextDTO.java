package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateExerciseTextDTO {
  private String id;
  private String text;
  private Integer orderNumber;
}
