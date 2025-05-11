package ch.uzh.ifi.imrg.platform.rest.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TherapySessionNoteOutputDTO {
  private String id;
  private String title;
  private String content;
}
