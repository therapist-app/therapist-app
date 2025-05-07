package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "therapy_session_notes")
public class TherapySessionNote {
  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column() private String title;

  @Column() private String note;

  @ManyToOne
  @JoinColumn(name = "therapy_session", referencedColumnName = "id")
  private TherapySession therapySession;
}
