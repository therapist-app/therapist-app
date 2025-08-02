package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextField;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "counseling_plan_phase_goals")
public class CounselingPlanPhaseGoal implements OwnedByTherapist, HasLLMContext {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Counseling Plan Phase Goal name", order = 1)
  @Column()
  @Lob
  private String goalName;

  @LLMContextField(label = "Counseling Plan Phase Goal description", order = 2)
  @Lob
  @Column()
  private String goalDescription;

  @Column() private Boolean isCompleted;

  @ManyToOne
  @JoinColumn(name = "counseling_plan_phase_id", referencedColumnName = "id")
  private CounselingPlanPhase counselingPlanPhase;

  @Override
  public String getOwningTherapistId() {
    return this.getCounselingPlanPhase().getCounselingPlan().getPatient().getTherapist().getId();
  }

  @Override
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);
    return sb.toString();
  }
}
