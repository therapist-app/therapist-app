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
@Table(name = "counseling_plans")
public class CounselingPlan {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  private Instant startOfTherapy;

  @OneToMany(mappedBy = "counselingPlan", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CounselingPlanPhase> counselingPlanPhases = new ArrayList<>();

  @OneToOne(fetch = FetchType.EAGER, mappedBy = "counselingPlan")
  private Patient patient;
}
