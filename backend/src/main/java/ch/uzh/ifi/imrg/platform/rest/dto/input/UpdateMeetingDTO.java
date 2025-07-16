package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
import java.time.Instant;
import lombok.Data;

@Data
public class UpdateMeetingDTO {
  private String id;
  private Instant meetingStart;
  private Instant meetingEnd;
  private String location;
  private MeetingStatus meetingStatus;
}
