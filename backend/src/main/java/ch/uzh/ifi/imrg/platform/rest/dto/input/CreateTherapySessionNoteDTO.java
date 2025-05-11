package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateTherapySessionNoteDTO {
  private String therapySessionId;
  private String title;
  private String content;
}
