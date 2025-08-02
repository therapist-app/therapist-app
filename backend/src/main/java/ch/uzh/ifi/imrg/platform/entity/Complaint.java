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
@Table(name = "complaints")
public class Complaint implements OwnedByTherapist, HasLLMContext {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Complaint main complaint", order = 1)
  @Lob
  private String mainComplaint;

  @LLMContextField(label = "Complaint duation", order = 2)
  @Lob
  private String duration;

  @LLMContextField(label = "Complaint onset", order = 3)
  @Lob
  private String onset;

  @LLMContextField(label = "Complaint course", order = 4)
  @Lob
  private String course;

  @LLMContextField(label = "Complaint precipitating factors", order = 5)
  @Lob
  private String precipitatingFactors;

  @LLMContextField(label = "Complaint aggravating relieving", order = 6)
  @Lob
  private String aggravatingRelieving;

  @LLMContextField(label = "Complaint timeline", order = 7)
  @Lob
  private String timeline;

  @LLMContextField(label = "Complaint disturbances", order = 8)
  @Lob
  private String disturbances;

  @LLMContextField(label = "Complaint suicidal ideation", order = 9)
  @Lob
  private String suicidalIdeation;

  @LLMContextField(label = "Complaint negative history", order = 10)
  @Lob
  private String negativeHistory;

  @ManyToOne
  @JoinColumn(name = "patient_id")
  private Patient patient;

  @Override
  public String getOwningTherapistId() {
    return this.getPatient().getTherapist().getId();
  }

  @Override
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);
    return sb.toString();
  }
}
