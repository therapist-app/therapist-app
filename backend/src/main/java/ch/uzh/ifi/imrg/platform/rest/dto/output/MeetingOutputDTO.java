package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
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
  private MeetingStatus meetingStatus;
  private List<MeetingNoteOutputDTO> meetingNotesOutputDTO;
}
