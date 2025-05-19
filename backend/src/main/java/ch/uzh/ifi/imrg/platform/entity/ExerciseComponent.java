package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;

@Data
@Entity
@Table(name = "exercise_components")
public class ExerciseComponent {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column
  private ExerciseComponentType exerciseComponentType;

  @Column
  private String description;

  private String fileName;

  private String fileType;

  @Lob
  private byte[] fileData;

  @Lob
  @Column
  private String extractedText;

  @Column()
  private Integer orderNumber;

  @ManyToOne
  @JoinColumn(name = "exercise_id", referencedColumnName = "id")
  private Exercise exercise;
}
