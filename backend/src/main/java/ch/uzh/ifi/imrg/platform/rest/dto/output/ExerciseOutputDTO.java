package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class ExerciseOutputDTO {
  private String id;
  private String exerciseTitle;
  private String exerciseDescription;
  private String exerciseExplanation;
  private Instant exerciseStart;
  private Instant exerciseEnd;
  private Boolean isPaused;
  private Integer doEveryNDays;
  private List<ExerciseComponentOutputDTO> exerciseComponentsOutputDTO;
}
