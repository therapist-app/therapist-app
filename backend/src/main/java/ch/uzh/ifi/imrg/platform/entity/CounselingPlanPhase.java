package ch.uzh.ifi.imrg.platform.entity;

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
public class CounselingPlanPhase {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column() private String phaseName;

  @Column() private Instant startDate;
  @Column() private Instant endDate;

  @OneToMany(
      mappedBy = "counselingPlanPhase",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<CounselingPlanPhaseGoal> phaseGoals = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "counseling_plan_id", referencedColumnName = "id")
  private CounselingPlan counselingPlan;

  @ManyToMany()
  @JoinTable(
      name = "exercises_to_counseling_plan_phases",
      joinColumns = @JoinColumn(name = "counseling_plan_phase_id"),
      inverseJoinColumns = @JoinColumn(name = "exercise_id"),
      uniqueConstraints =
          @UniqueConstraint(columnNames = {"counseling_plan_phase_id", "exercise_id"}))
  private List<Exercise> phaseExercises = new ArrayList<>();
}
