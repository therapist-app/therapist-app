package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateMeetingNoteDTO {
  private String meetingId;
  private String title;
  private String content;
}
