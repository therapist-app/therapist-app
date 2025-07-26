package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextField;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
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
public class Therapist implements Serializable, HasLLMContext {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Coach email", order = 2)
  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private LLMModel llmModel;

  @OneToMany(
      mappedBy = "therapist",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("name ASC")
  private List<Patient> patients = new ArrayList<>();

  @OneToMany(
      mappedBy = "therapist",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("createdAt DESC")
  private List<ChatbotTemplate> chatbotTemplates = new ArrayList<>();

  @OneToMany(
      mappedBy = "therapist",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("createdAt DESC")
  private List<TherapistDocument> therapistDocuments = new ArrayList<>();

  @Override
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);

    LLMContextBuilder.addLLMContextOfListOfEntities(sb, this.patients, "Client", level);

    LLMContextBuilder.addLLMContextOfListOfEntities(
        sb, this.therapistDocuments, "Coach Document", level);

    return sb.toString();
  }
}
