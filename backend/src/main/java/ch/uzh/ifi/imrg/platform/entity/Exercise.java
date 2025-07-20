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
@Table(name = "exercises")
public class Exercise implements OwnedByTherapist {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column() private String exerciseTitle;

  @Lob @Column() private String exerciseDescription;

  // used in the system prompt
  @Lob @Column() private String exerciseExplanation;

  @Column(name = "exercise_start")
  private Instant exerciseStart;

  @Column(name = "exercise_end")
  private Instant exerciseEnd;

  @Column() private Boolean isPaused;

  @Column() private Integer doEveryNDays;

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
}
