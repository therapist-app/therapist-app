package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;

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

  public String getTestId() {
    return testId;
  }

  public void setTestId(String testId) {
    this.testId = testId;
  }

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

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
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
