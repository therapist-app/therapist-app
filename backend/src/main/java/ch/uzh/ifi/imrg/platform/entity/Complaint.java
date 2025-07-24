package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.utils.LLMContextField;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "complaints")
public class Complaint implements OwnedByTherapist, HasLLMContext {

  public static final Integer HIERARCHY_LEVEL = Patient.HIERARCHY_LEVEL + 1;

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @LLMContextField(label = "Complaint main complaint", order = 1)
  private String mainComplaint;

  @LLMContextField(label = "Complaint duation", order = 2)
  private String duration;

  @LLMContextField(label = "Complaint onset", order = 3)
  private String onset;

  @LLMContextField(label = "Complaint course", order = 4)
  private String course;

  @LLMContextField(label = "Complaint precipitating factors", order = 5)
  private String precipitatingFactors;

  @LLMContextField(label = "Complaint aggravating relieving", order = 6)
  private String aggravatingRelieving;

  @LLMContextField(label = "Complaint timeline", order = 7)
  private String timeline;

  @LLMContextField(label = "Complaint disturbances", order = 8)
  private String disturbances;

  @LLMContextField(label = "Complaint suicidal ideation", order = 9)
  private String suicidalIdeation;

  @LLMContextField(label = "Complaint negative history", order = 10)
  private String negativeHistory;

  @ManyToOne
  @JoinColumn(name = "patient_id")
  private Patient patient;

  @Override
  public String getOwningTherapistId() {
    return this.getPatient().getTherapist().getId();
  }

  @Override
  public String toLLMContext() {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, HIERARCHY_LEVEL);
    return sb.toString();
  }
}
