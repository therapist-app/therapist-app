package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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

  public String getPatientId() {
    return patientId;
  }

  public void setPatientId(String patientId) {
    this.patientId = patientId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public int getQuestion1() {
    return question1;
  }

  public void setQuestion1(int question1) {
    this.question1 = question1;
  }

  public int getQuestion2() {
    return question2;
  }

  public void setQuestion2(int question2) {
    this.question2 = question2;
  }

  public int getQuestion3() {
    return question3;
  }

  public void setQuestion3(int question3) {
    this.question3 = question3;
  }

  public int getQuestion4() {
    return question4;
  }

  public void setQuestion4(int question4) {
    this.question4 = question4;
  }

  public int getQuestion5() {
    return question5;
  }

  public void setQuestion5(int question5) {
    this.question5 = question5;
  }

  public int getQuestion6() {
    return question6;
  }

  public void setQuestion6(int question6) {
    this.question6 = question6;
  }

  public int getQuestion7() {
    return question7;
  }

  public void setQuestion7(int question7) {
    this.question7 = question7;
  }
}
