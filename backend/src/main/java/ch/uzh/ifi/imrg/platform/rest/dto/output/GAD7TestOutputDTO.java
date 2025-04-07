package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GAD7TestOutputDTO {

  private String testId;
  private String patientId;
  private String sessionId;
  private LocalDateTime creationDate;
  private int question1;
  private int question2;
  private int question3;
  private int question4;
  private int question5;
  private int question6;
  private int question7;

}
