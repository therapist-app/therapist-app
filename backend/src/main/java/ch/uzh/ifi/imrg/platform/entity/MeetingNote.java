package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "meeting_notes")
public class MeetingNote {
  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column()
  private String title;

  @Column()
  private String content;

  @ManyToOne
  @JoinColumn(name = "meeting_id", referencedColumnName = "id")
  private Meeting meeting;
}
