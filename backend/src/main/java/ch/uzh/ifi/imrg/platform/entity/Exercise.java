package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "exercises")
public class Exercise {

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

  @Column() private ExerciseType exerciseType;

  @OneToMany(
      mappedBy = "exercise",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<ExerciseComponent> exerciseComponents = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "therapy_session_id", referencedColumnName = "id")
  private Patient patient;
}
