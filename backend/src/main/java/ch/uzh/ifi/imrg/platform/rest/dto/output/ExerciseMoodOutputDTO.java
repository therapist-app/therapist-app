package ch.uzh.ifi.imrg.platform.rest.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseMoodOutputDTO {
  private String moodName;
  private int moodScore;
}
