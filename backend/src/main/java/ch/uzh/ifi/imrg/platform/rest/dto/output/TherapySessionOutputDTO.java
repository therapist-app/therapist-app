package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class TherapySessionOutputDTO {

  private String id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime sessionStart;
  private LocalDateTime sessionEnd;
  private List<TherapySessionNoteOutputDTO> therapySessionNotesOutputDTO;
}
