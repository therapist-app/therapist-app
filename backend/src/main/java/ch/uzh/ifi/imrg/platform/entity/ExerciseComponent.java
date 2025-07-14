package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "exercise_components")
public class ExerciseComponent implements OwnedByTherapist {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column private ExerciseComponentType exerciseComponentType;

  @Column private String description;

  private String fileName;

  private String fileType;

  @Lob private byte[] fileData;

  @Lob @Column private String extractedText;

  @Column() private Integer orderNumber;

  @ManyToOne
  @JoinColumn(name = "exercise_id", referencedColumnName = "id")
  private Exercise exercise;

  @Override
  public String getOwningTherapistId() {
    return this.getExercise().getPatient().getTherapist().getId();
  }
}
