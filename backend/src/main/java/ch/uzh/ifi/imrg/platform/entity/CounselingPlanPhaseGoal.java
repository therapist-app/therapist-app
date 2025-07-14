package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "counseling_plan_phase_goals")
public class CounselingPlanPhaseGoal implements OwnedByTherapist {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column()
  private String goalName;

  @Column()
  private String goalDescription;

  @ManyToOne
  @JoinColumn(name = "counseling_plan_phase_id", referencedColumnName = "id")
  private CounselingPlanPhase counselingPlanPhase;

  @Override
  public String getOwningTherapistId() {
    return this.getCounselingPlanPhase().getCounselingPlan().getPatient().getTherapist().getId();
  }
}
