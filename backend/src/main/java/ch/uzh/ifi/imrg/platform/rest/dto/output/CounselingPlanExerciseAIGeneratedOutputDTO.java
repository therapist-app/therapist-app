package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import java.time.Instant;
import lombok.Data;

@Data
public class CounselingPlanExerciseAIGeneratedOutputDTO {
  private String selectedMeetingId;
  private String title;
  private ExerciseType exerciseType;
  private Instant exerciseStart;
  private Instant exerciseEnd;
}
