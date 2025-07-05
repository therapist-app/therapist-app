package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.Instant;
import lombok.Data;

@Data
public class CreateMeetingDTO {

  private Instant meetingStart;

  private Instant meetingEnd;

  private String location;

  private String patientId;
}
