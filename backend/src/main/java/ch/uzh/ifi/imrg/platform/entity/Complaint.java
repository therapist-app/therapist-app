package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.utils.LLMContextField;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "complaints")
public class Complaint implements OwnedByTherapist {

  public static final Integer HIERARCHY_LEVEL = Patient.HIERARCHY_LEVEL + 1;

  @LLMContextField(label = "Complaint ID", order = 1)
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @LLMContextField(label = "Complaint main complaint", order = 2)
  private String mainComplaint;

  @LLMContextField(label = "Complaint duation", order = 3)
  private String duration;

  @LLMContextField(label = "Complaint onset", order = 4)
  private String onset;

  @LLMContextField(label = "Complaint course", order = 5)
  private String course;

  @LLMContextField(label = "Complaint precipitating factors", order = 6)
  private String precipitatingFactors;

  @LLMContextField(label = "Complaint aggravating relieving", order = 7)
  private String aggravatingRelieving;

  @LLMContextField(label = "Complaint timeline", order = 8)
  private String timeline;

  @LLMContextField(label = "Complaint disturbances", order = 9)
  private String disturbances;

  @LLMContextField(label = "Complaint suicidal ideation", order = 10)
  private String suicidalIdeation;

  @LLMContextField(label = "Complaint negative history", order = 11)
  private String negativeHistory;

  @ManyToOne
  @JoinColumn(name = "patient_id")
  private Patient patient;

  @Override
  public String getOwningTherapistId() {
    return this.getPatient().getTherapist().getId();
  }

  public String toLLMContext() {
    return FormatUtil.indentBlock(LLMContextBuilder.build(this), HIERARCHY_LEVEL);
  }
}
