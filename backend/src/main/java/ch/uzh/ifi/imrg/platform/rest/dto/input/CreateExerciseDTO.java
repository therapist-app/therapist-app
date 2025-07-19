package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.Instant;
import lombok.Data;

@Data
public class CreateExerciseDTO {
  private String patientId;
  private String exerciseTitle;
  private Instant exerciseStart;
  private long durationInWeeks;
  private Integer doEveryNDays;
}
