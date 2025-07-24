package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.utils.LLMContextField;
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

  public static final Integer HIERARCHY_LEVEL = Therapist.HIERARCHY_LEVEL + 1;

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

  @LLMContextField(label = "Patient name", order = 2)
  @Column(nullable = false)
  private String name;

  @LLMContextField(label = "Patient gender", order = 3)
  @Column(nullable = true)
  private String gender;

  @LLMContextField(label = "Patient age", order = 4)
  @Column(nullable = true)
  private int age;

  @LLMContextField(label = "Patient phone number", order = 6)
  @Column(nullable = true)
  private String phoneNumber;

  @LLMContextField(label = "Patient email", order = 7)
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String initialPassword;

  @LLMContextField(label = "Patient address", order = 8)
  @Column(nullable = true)
  private String address;

  @LLMContextField(label = "Patient marital status", order = 9)
  @Column
  private String maritalStatus;

  @LLMContextField(label = "Patient religion", order = 10)
  @Column
  private String religion;

  @LLMContextField(label = "Patient education", order = 11)
  @Column
  private String education;

  @LLMContextField(label = "Patient occupation", order = 12)
  @Column
  private String occupation;

  @LLMContextField(label = "Patient income", order = 13)
  @Column
  private String income;

  @LLMContextField(label = "Patient date of admission", order = 14)
  @Column
  private String dateOfAdmission;

  @LLMContextField(label = "Patient treatment past", order = 15)
  @Lob
  @Column
  private String treatmentPast;

  @LLMContextField(label = "Patient treatment current", order = 16)
  @Lob
  @Column
  private String treatmentCurrent;

  @LLMContextField(label = "Patient past medical", order = 17)
  @Lob
  @Column
  private String pastMedical;

  @LLMContextField(label = "Patient past psych", order = 18)
  @Lob
  @Column
  private String pastPsych;

  @LLMContextField(label = "Patient family illness", order = 19)
  @Lob
  @Column
  private String familyIllness;

  @LLMContextField(label = "Patient family social", order = 20)
  @Lob
  @Column
  private String familySocial;

  @LLMContextField(label = "Patient personal perinatal", order = 21)
  @Lob
  @Column
  private String personalPerinatal;

  @LLMContextField(label = "Patient personal childhood", order = 22)
  @Lob
  @Column
  private String personalChildhood;

  @LLMContextField(label = "Patient personal education", order = 23)
  @Lob
  @Column
  private String personalEducation;

  @LLMContextField(label = "Patient personal play", order = 24)
  @Lob
  @Column
  private String personalPlay;

  @LLMContextField(label = "Patient personal adolescence", order = 25)
  @Lob
  @Column
  private String personalAdolescence;

  @LLMContextField(label = "Patient personal puberty", order = 26)
  @Lob
  @Column
  private String personalPuberty;

  @LLMContextField(label = "Patient personal obstetric", order = 27)
  @Lob
  @Column
  private String personalObstetric;

  @LLMContextField(label = "Patient personal occupational", order = 28)
  @Lob
  @Column
  private String personalOccupational;

  @LLMContextField(label = "Patient personal marital", order = 29)
  @Lob
  @Column
  private String personalMarital;

  @LLMContextField(label = "Patient personal premorbid", order = 30)
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
  public String toLLMContext() {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, HIERARCHY_LEVEL);
    LLMContextBuilder.addLLMContextOfListOfEntities(sb, complaints, "Complaint", HIERARCHY_LEVEL);
    LLMContextBuilder.addLLMContextOfListOfEntities(sb, meetings, "Meeting", HIERARCHY_LEVEL);
    LLMContextBuilder.addLLMContextOfListOfEntities(sb, exercises, "Exercise", HIERARCHY_LEVEL);

    sb.append(
        sb.append(
            FormatUtil.indentBlock(
                "\n--- " + "Counseling Plan" + "s ---\n", HIERARCHY_LEVEL, false)));
    sb.append(counselingPlan.toLLMContext());

    return sb.toString();
  }
}
