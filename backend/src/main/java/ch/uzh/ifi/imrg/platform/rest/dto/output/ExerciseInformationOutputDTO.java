package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseInformationOutputDTO {
  private Instant startTime;
  private Instant endTime;
  private String feedback;
  private List<SharedInputFieldOutputDTO> sharedInputFields;
  private List<ExerciseMoodOutputDTO> moodsBefore;
  private List<ExerciseMoodOutputDTO> moodsAfter;
}
