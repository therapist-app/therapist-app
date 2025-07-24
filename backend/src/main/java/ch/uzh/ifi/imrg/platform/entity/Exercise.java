package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.utils.LLMContextField;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "exercises")
public class Exercise implements OwnedByTherapist {

  @LLMContextField(label = "Exercise ID", order = 1)
  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Exercise Title", order = 2)
  @Column()
  private String exerciseTitle;

  @LLMContextField(label = "Exercise Description", order = 3)
  @Lob
  @Column()
  private String exerciseDescription;

  // used in the system prompt
  @LLMContextField(label = "Exercise Explanation", order = 4)
  @Lob
  @Column()
  private String exerciseExplanation;

  @LLMContextField(label = "Exercise Start", order = 5)
  @Column(name = "exercise_start")
  private Instant exerciseStart;

  @LLMContextField(label = "Exercise End", order = 6)
  @Column(name = "exercise_end")
  private Instant exerciseEnd;

  @LLMContextField(label = "Exercise is Paused", order = 7)
  @Column()
  private Boolean isPaused;

  @LLMContextField(label = "Exercise do every N Days", order = 8)
  @Column()
  private Integer doEveryNDays;

  @OneToMany(
      mappedBy = "exercise",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<ExerciseComponent> exerciseComponents = new ArrayList<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  private Patient patient;

  @ManyToMany(mappedBy = "phaseExercises")
  private List<CounselingPlanPhase> counselingPlanPhases;

  @Override
  public String getOwningTherapistId() {
    return this.getPatient().getTherapist().getId();
  }

  public String toLLMContext() {
    StringBuilder sb = new StringBuilder(LLMContextBuilder.build(this));

    if (!this.exerciseComponents.isEmpty()) {
      sb.append("\n--- Exercise Components ---\n");
      for (ExerciseComponent component : this.exerciseComponents) {
        sb.append(component.toLLMContext());
      }
    }

    return sb.toString();
  }
}
