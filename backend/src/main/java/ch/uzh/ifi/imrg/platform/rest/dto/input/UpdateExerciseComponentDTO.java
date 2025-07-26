package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateExerciseComponentDTO {
  private String id;
  private String exerciseComponentDescription;
  private String youtubeUrl;
  private Integer orderNumber;
}
