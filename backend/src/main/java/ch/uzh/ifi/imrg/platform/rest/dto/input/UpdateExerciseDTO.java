package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.Instant;
import lombok.Data;

@Data
public class UpdateExerciseDTO {
  private String id;
  private String exerciseTitle;
  private String exerciseDescription;
  private String exerciseExplanation;
  private Instant exerciseStart;
  private Instant exerciseEnd;
  private Boolean isPaused;
  private Integer doEveryNDays;
}
