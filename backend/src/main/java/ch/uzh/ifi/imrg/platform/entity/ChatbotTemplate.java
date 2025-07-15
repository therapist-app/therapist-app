package ch.uzh.ifi.imrg.platform.entity;

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
@Table(name = "chatbot_templates")
public class ChatbotTemplate implements Serializable, OwnedByTherapist {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column(nullable = false)
  private String chatbotName;

  private String chatbotIcon;
  private String chatbotRole;
  private String chatbotTone;
  private String welcomeMessage;

  @ManyToOne
  @JoinColumn(name = "therapist_id")
  private Therapist therapist;

  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  private Patient patient;

  @OneToMany(
      mappedBy = "chatbotTemplate",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<ChatbotTemplateDocument> chatbotTemplateDocuments = new ArrayList<>();

  @Override
  public String getOwningTherapistId() {
    return this.getTherapist().getId();
  }
}
