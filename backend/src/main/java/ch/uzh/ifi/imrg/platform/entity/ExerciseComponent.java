package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.enums.ExerciseComponentType;
import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
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
public class ExerciseComponent implements OwnedByTherapist {

  public static final Integer HIERARCHY_LEVEL = Exercise.HIERARCHY_LEVEL + 1;

  @LLMContextField(label = "Exercise Component ID", order = 1)
  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Exercise Component Type", order = 2)
  @Column
  private ExerciseComponentType exerciseComponentType;

  @LLMContextField(label = "Exercise Component Description", order = 3)
  @Lob
  @Column
  private String exerciseComponentDescription;

  @LLMContextField(label = "Exercise Component File Name", order = 4)
  private String fileName;

  @LLMContextField(label = "Exercise Component File Type", order = 5)
  private String fileType;

  @Lob private byte[] fileData;

  @LLMContextField(label = "Exercise Component Extracted Text", order = 6)
  @Lob
  @Column
  private String extractedText;

  @LLMContextField(label = "Exercise Component Order Number", order = 7)
  @Column()
  private Integer orderNumber;

  @ManyToOne
  @JoinColumn(name = "exercise_id", referencedColumnName = "id")
  private Exercise exercise;

  @Override
  public String getOwningTherapistId() {
    return this.getExercise().getPatient().getTherapist().getId();
  }

  public String toLLMContext() {
    return FormatUtil.indentBlock(LLMContextBuilder.build(this), HIERARCHY_LEVEL);
  }
}
