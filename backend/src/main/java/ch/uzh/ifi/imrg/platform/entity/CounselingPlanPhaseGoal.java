package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
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

  public static final Integer HIERARCHY_LEVEL = CounselingPlanPhase.HIERARCHY_LEVEL + 1;

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column() private String goalName;

  @Lob @Column() private String goalDescription;

  @ManyToOne
  @JoinColumn(name = "counseling_plan_phase_id", referencedColumnName = "id")
  private CounselingPlanPhase counselingPlanPhase;

  @Override
  public String getOwningTherapistId() {
    return this.getCounselingPlanPhase().getCounselingPlan().getPatient().getTherapist().getId();
  }

  @Override
  public String toLLMContext() {
    return FormatUtil.indentBlock(LLMContextBuilder.build(this), HIERARCHY_LEVEL, false);
  }
}
