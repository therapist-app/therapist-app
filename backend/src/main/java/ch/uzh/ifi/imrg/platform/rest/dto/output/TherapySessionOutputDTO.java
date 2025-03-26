package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;

public class TherapySessionOutputDTO {

  private String id;

  private LocalDateTime sessionStart;

  private LocalDateTime sessionEnd;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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
}
