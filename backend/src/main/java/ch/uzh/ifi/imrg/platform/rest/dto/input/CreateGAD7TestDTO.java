package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGAD7TestDTO {

  private String patientId;
  private String sessionId;

  @Min(0)
  @Max(3)
  private int question1;

  @Min(0)
  @Max(3)
  private int question2;

  @Min(0)
  @Max(3)
  private int question3;

  @Min(0)
  @Max(3)
  private int question4;

  @Min(0)
  @Max(3)
  private int question5;

  @Min(0)
  @Max(3)
  private int question6;

  @Min(0)
  @Max(3)
  private int question7;

  public CreateGAD7TestDTO() {}
}
