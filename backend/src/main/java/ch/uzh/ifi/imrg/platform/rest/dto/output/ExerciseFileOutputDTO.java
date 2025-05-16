package ch.uzh.ifi.imrg.platform.rest.dto.output;

import lombok.Data;

@Data
public class ExerciseFileOutputDTO {
  private String id;
  private String fileName;
  private String fileType;
  private String extractedText;
  private String description;
  private Integer orderNumber;
}
