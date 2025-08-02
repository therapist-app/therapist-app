package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextField;
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
@Table(name = "counseling_plan_phases")
public class CounselingPlanPhase implements OwnedByTherapist, HasLLMContext {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Counseling Plan phase name", order = 1)
  @Column()
  @Lob
  private String phaseName;

  @LLMContextField(label = "Counseling Plan phase duration in weeks", order = 2)
  @Column()
  private int durationInWeeks;

  @LLMContextField(label = "Counseling Plan phase number", order = 3)
  @Column()
  private int phaseNumber;

  @ManyToOne
  @JoinColumn(name = "counseling_plan_id", referencedColumnName = "id")
  private CounselingPlan counselingPlan;

  @OneToMany(
      mappedBy = "counselingPlanPhase",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("createdAt ASC")
  private List<CounselingPlanPhaseGoal> phaseGoals = new ArrayList<>();

  @ManyToMany()
  @JoinTable(
      name = "exercises_to_counseling_plan_phases",
      joinColumns = @JoinColumn(name = "counseling_plan_phase_id"),
      inverseJoinColumns = @JoinColumn(name = "exercise_id"),
      uniqueConstraints =
          @UniqueConstraint(columnNames = {"counseling_plan_phase_id", "exercise_id"}))
  @OrderBy("exerciseStart DESC")
  private List<Exercise> phaseExercises = new ArrayList<>();

  @Override
  public String getOwningTherapistId() {
    return this.getCounselingPlan().getPatient().getTherapist().getId();
  }

  @Override
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);
    LLMContextBuilder.addLLMContextOfListOfEntities(
        sb, phaseGoals, "Counseling Plan Phase Goal", level);
    LLMContextBuilder.addLLMContextOfListOfEntities(
        sb, phaseExercises, "Counseling Plan Exercise", level);

    return sb.toString();
  }
}
