package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.utils.LLMContextField;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "therapists")
public class Therapist implements Serializable {

  @LLMContextField(label = "Therapist ID", order = 1)
  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Therapist email", order = 2)
  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToMany(
      mappedBy = "therapist",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Patient> patients = new ArrayList<>();

  @OneToMany(
      mappedBy = "therapist",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<ChatbotTemplate> chatbotTemplates = new ArrayList<>();

  @OneToMany(
      mappedBy = "therapist",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<TherapistDocument> therapistDocuments = new ArrayList<>();

  public String toLLMContext() {
    StringBuilder sb = new StringBuilder(LLMContextBuilder.build(this));

    if (!this.patients.isEmpty()) {
      sb.append("\n--- Patients ---\n");
      for (Patient patient : this.patients) {
        sb.append(patient.toLLMContext());
      }
    }

    return sb.toString();
  }
}
