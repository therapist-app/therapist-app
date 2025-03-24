package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.time.LocalDateTime;

public class CreateTherapySessionDTO {

  private LocalDateTime sessionStart;

  private LocalDateTime sessionEnd;

  private String patientId;

  public LocalDateTime getSessionStart() {
    return sessionStart;
  }

  public void setSessionStart(LocalDateTime sessionStart) {
    this.sessionStart = sessionStart;
  }

  public LocalDateTime getSessionEnd() {
    return sessionEnd;
  }

  public void setSessionEnd(LocalDateTime sessionEnd) {
    this.sessionEnd = sessionEnd;
  }

  public String getPatientId() {
    return this.patientId;
  }

  public void setPatientId(String patientId) {
    this.patientId = patientId;
  }
}
