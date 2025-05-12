package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "patients")
public class Patient implements Serializable {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String gender;

  @Column(nullable = true)
  private int age;

  @Column(nullable = true)
  private String phoneNumber;

  @Column(nullable = true)
  private String email;

  @Column(nullable = true)
  private String address;

  @Column(nullable = true)
  private String description;

  @ManyToOne
  @JoinColumn(name = "therapist_id", referencedColumnName = "id")
  private Therapist therapist;

  @Column(unique = true)
  private String workspaceId = UUID.randomUUID().toString();

  @OneToMany(
      mappedBy = "patient",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<TherapySession> therapySessions = new ArrayList<>();

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
}
