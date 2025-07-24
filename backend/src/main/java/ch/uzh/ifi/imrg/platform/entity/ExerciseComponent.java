package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;
import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.utils.LLMContextField;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "exercise_components")
public class ExerciseComponent implements OwnedByTherapist, HasLLMContext {

  public static final Integer HIERARCHY_LEVEL = Exercise.HIERARCHY_LEVEL + 1;

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Exercise Component type", order = 1)
  @Column
  private ExerciseComponentType exerciseComponentType;

  @LLMContextField(label = "Exercise Component description", order = 2)
  @Lob
  @Column
  private String exerciseComponentDescription;

  @LLMContextField(label = "Exercise Component file name", order = 3)
  private String fileName;

  @LLMContextField(label = "Exercise Component file type", order = 4)
  private String fileType;

  @Lob private byte[] fileData;

  @LLMContextField(label = "Exercise Component extracted text", order = 5)
  @Lob
  @Column
  private String extractedText;

  @LLMContextField(label = "Exercise Component order number", order = 6)
  @Column()
  private Integer orderNumber;

  @ManyToOne
  @JoinColumn(name = "exercise_id", referencedColumnName = "id")
  private Exercise exercise;

  @Override
  public String getOwningTherapistId() {
    return this.getExercise().getPatient().getTherapist().getId();
  }

  @Override
  public String toLLMContext() {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, HIERARCHY_LEVEL);
    return sb.toString();
  }
}
