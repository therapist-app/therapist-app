package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextField;
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
public class ExerciseComponent implements OwnedByTherapist, HasLLMContext {

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

  @LLMContextField(label = "Exercise Youtube URL", order = 3)
  @Column
  @Lob
  private String youtubeUrl;

  @LLMContextField(label = "Exercise Component file name", order = 4)
  @Lob
  private String fileName;

  @LLMContextField(label = "Exercise Component file type", order = 5)
  @Lob
  private String fileType;

  @Lob private byte[] fileData;

  @LLMContextField(label = "Exercise Component extracted text", order = 6)
  @Lob
  @Column
  private String extractedText;

  @LLMContextField(label = "Exercise Component order number", order = 7)
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
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);
    return sb.toString();
  }
}
