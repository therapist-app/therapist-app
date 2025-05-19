package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;
import lombok.Data;

@Data
public class ExerciseComponentOutputDTO {
  private String id;
  private ExerciseComponentType exerciseComponentType;
  private String description;
  private String fileName;
  private String fileType;
  private String extractedText;
  private Integer orderNumber;
}
