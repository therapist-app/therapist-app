package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeetingNoteOutputDTO {
  private String id;
  private Instant createdAt;
  private Instant updatedAt;
  private String title;
  private String content;
}
