package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "therapy_sessions")
public class TherapySession implements Serializable {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column(name = "session_start")
  private LocalDateTime sessionStart;

  @Column(name = "session_end")
  private LocalDateTime sessionEnd;

  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  private Patient patient;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
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
