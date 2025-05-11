package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateTherapySessionNoteDTO {
  private String id;
  private String title;
  private String content;
}
