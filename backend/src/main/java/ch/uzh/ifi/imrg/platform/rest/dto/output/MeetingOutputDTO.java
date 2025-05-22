package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class MeetingOutputDTO {

  private String id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime meetingStart;
  private LocalDateTime meetingEnd;
  private List<MeetingNoteOutputDTO> meetingNotesOutputDTO;
}
