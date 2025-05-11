package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TherapySessionNoteOutputDTO {
  private String id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String title;
  private String content;
}
