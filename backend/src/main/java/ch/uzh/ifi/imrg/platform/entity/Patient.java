package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextField;
import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "patients")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Patient implements Serializable, OwnedByTherapist, HasLLMContext {

  @Id
  @Column(unique = true)
  @EqualsAndHashCode.Include
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Client name", order = 1)
  @Column(nullable = false)
  private String name;

  @LLMContextField(label = "Client gender", order = 2)
  @Column(nullable = true)
  private String gender;

  @LLMContextField(label = "Client age", order = 3)
  @Column(nullable = true)
  private int age;

  @LLMContextField(label = "Client phone number", order = 4)
  @Column(nullable = true)
  private String phoneNumber;

  @LLMContextField(label = "Client email", order = 5)
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String initialPassword;

  @LLMContextField(label = "Client address", order = 6)
  @Column(nullable = true)
  private String address;

  @LLMContextField(label = "Client marital status", order = 7)
  @Column
  private String maritalStatus;

  @LLMContextField(label = "Client religion", order = 8)
  @Column
  private String religion;

  @LLMContextField(label = "Client education", order = 9)
  @Column
  private String education;

  @LLMContextField(label = "Client occupation", order = 10)
  @Column
  private String occupation;

  @LLMContextField(label = "Client income", order = 11)
  @Column
  private String income;

  @LLMContextField(label = "Client date of admission", order = 12)
  @Column
  private String dateOfAdmission;

  @LLMContextField(label = "Client treatment past", order = 13)
  @Lob
  @Column
  private String treatmentPast;

  @LLMContextField(label = "Client treatment current", order = 14)
  @Lob
  @Column
  private String treatmentCurrent;

  @LLMContextField(label = "Client past medical", order = 15)
  @Lob
  @Column
  private String pastMedical;

  @LLMContextField(label = "Client past psych", order = 16)
  @Lob
  @Column
  private String pastPsych;

  @LLMContextField(label = "Client family illness", order = 17)
  @Lob
  @Column
  private String familyIllness;

  @LLMContextField(label = "Client family social", order = 18)
  @Lob
  @Column
  private String familySocial;

  @LLMContextField(label = "Client personal perinatal", order = 19)
  @Lob
  @Column
  private String personalPerinatal;

  @LLMContextField(label = "Client personal childhood", order = 20)
  @Lob
  @Column
  private String personalChildhood;

  @LLMContextField(label = "Client personal education", order = 21)
  @Lob
  @Column
  private String personalEducation;

  @LLMContextField(label = "Client personal play", order = 22)
  @Lob
  @Column
  private String personalPlay;

  @LLMContextField(label = "Client personal adolescence", order = 23)
  @Lob
  @Column
  private String personalAdolescence;

  @LLMContextField(label = "Client personal puberty", order = 24)
  @Lob
  @Column
  private String personalPuberty;

  @LLMContextField(label = "Client personal obstetric", order = 25)
  @Lob
  @Column
  private String personalObstetric;

  @LLMContextField(label = "Client personal occupational", order = 26)
  @Lob
  @Column
  private String personalOccupational;

  @LLMContextField(label = "Client personal marital", order = 27)
  @Lob
  @Column
  private String personalMarital;

  @LLMContextField(label = "Client personal premorbid", order = 28)
  @Lob
  @Column
  private String personalPremorbid;

  @OneToMany(
      mappedBy = "patient",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<Complaint> complaints = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "therapist_id")
  private Therapist therapist;

  @OneToMany(
      mappedBy = "patient",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Meeting> meetings = new ArrayList<>();

  @OneToMany(
      mappedBy = "patient",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<PatientDocument> patientDocuments = new ArrayList<>();

  @OneToMany(
      mappedBy = "patient",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<ChatbotTemplate> chatbotTemplates = new ArrayList<>();

  @OneToMany(
      mappedBy = "patient",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Exercise> exercises = new ArrayList<>();

  @OneToMany(
      mappedBy = "patient",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<GAD7Test> GAD7Tests = new ArrayList<>();

  @OneToOne(optional = false)
  @JoinColumn(name = "counseling_plan_id", referencedColumnName = "id")
  private CounselingPlan counselingPlan;

  @Override
  public String getOwningTherapistId() {
    return this.getTherapist().getId();
  }

  @Override
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);
    LLMContextBuilder.addLLMContextOfListOfEntities(sb, complaints, "Complaint", level);
    LLMContextBuilder.addLLMContextOfListOfEntities(sb, meetings, "Meeting", level);
    LLMContextBuilder.addLLMContextOfListOfEntities(sb, exercises, "Exercise", level);

    if (!counselingPlan.getCounselingPlanPhases().isEmpty()) {
      sb.append(FormatUtil.indentBlock("\n--- Counseling Plan ---\n", level, false));
      sb.append(counselingPlan.toLLMContext(level + 1));
    }

    LLMContextBuilder.addLLMContextOfListOfEntities(sb, chatbotTemplates, "Chatbot", level);

    LLMContextBuilder.addLLMContextOfListOfEntities(sb, patientDocuments, "Client Document", level);

    return sb.toString();
  }
}
