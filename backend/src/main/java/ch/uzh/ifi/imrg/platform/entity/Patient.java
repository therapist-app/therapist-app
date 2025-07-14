package ch.uzh.ifi.imrg.platform.entity;

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
public class Patient implements Serializable, OwnedByTherapist {

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

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String gender;

  @Column(nullable = true)
  private int age;

  @Column(nullable = true)
  private String phoneNumber;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String initialPassword;

  @Column(nullable = true)
  private String address;

  @Column private String maritalStatus;
  @Column private String religion;
  @Column private String education;
  @Column private String occupation;
  @Column private String income;
  @Column private String dateOfAdmission;

  @OneToMany(
      mappedBy = "patient",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<Complaint> complaints = new ArrayList<>();

  @Column private String treatmentPast;
  @Column private String treatmentCurrent;
  @Column private String pastMedical;
  @Column private String pastPsych;

  @Column private String familyIllness;
  @Column private String familySocial;

  @Column private String personalPerinatal;
  @Column private String personalChildhood;
  @Column private String personalEducation;
  @Column private String personalPlay;
  @Column private String personalAdolescence;
  @Column private String personalPuberty;
  @Column private String personalObstetric;
  @Column private String personalOccupational;
  @Column private String personalMarital;
  @Column private String personalPremorbid;

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
}
