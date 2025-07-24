package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PsychologicalTestOutputDTO {

  private String id;
  private String name;
  private String description;
  private String patientId;
  private Instant completedAt;
  private List<PsychologicalTestQuestionOutputDTO> questions;

  public PsychologicalTestOutputDTO(String name) {
    this.name = name;
  }
}
