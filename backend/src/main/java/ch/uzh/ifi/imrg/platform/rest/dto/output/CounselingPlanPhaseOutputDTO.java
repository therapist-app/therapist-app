package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class CounselingPlanPhaseOutputDTO {
  private String id;
  private String phaseName;
  private int durationInWeeks;
  private int phaseNumber;
  private Instant startDate;
  private Instant endDate;
  private List<ExerciseOutputDTO> phaseExercisesOutputDTO;
  private List<CounselingPlanPhaseGoalOutputDTO> phaseGoalsOutputDTO;
}
