package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class MeetingOutputDTO {

  private String id;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant meetingStart;
  private Instant meetingEnd;
  private String location;
  private List<MeetingNoteOutputDTO> meetingNotesOutputDTO;
}
