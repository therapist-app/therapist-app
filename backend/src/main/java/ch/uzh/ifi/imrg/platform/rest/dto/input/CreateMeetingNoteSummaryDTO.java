package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import lombok.Data;

@Data
public class CreateMeetingNoteSummaryDTO {
  private String meetingId;
  private Language language;
}
