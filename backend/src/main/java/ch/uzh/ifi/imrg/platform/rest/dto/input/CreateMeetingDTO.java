package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateMeetingDTO {

  private LocalDateTime meetingStart;

  private LocalDateTime meetingEnd;

  private String patientId;
}
