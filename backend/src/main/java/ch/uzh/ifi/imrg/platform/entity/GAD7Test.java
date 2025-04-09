package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "gad7_tests")
public class GAD7Test {

  @Id
  @Column(name = "test_id", unique = true)
  private String testId = UUID.randomUUID().toString();

  @CreationTimestamp
  @Column(name = "creation_date", updatable = false)
  private LocalDateTime creationDate;

  // Associations to Patient and TherapySession
  @ManyToOne
  @JoinColumn(name = "patient_id", nullable = false)
  private Patient patient;

  @ManyToOne
  @JoinColumn(name = "session_id", nullable = false)
  private TherapySession therapySession;

  @Column(nullable = false)
  private int question1;

  @Column(nullable = false)
  private int question2;

  @Column(nullable = false)
  private int question3;

  @Column(nullable = false)
  private int question4;

  @Column(nullable = false)
  private int question5;

  @Column(nullable = false)
  private int question6;

  @Column(nullable = false)
  private int question7;
}
