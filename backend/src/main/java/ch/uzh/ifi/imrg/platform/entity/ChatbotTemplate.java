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
@Table(name = "chatbot_templates")
public class ChatbotTemplate implements Serializable, OwnedByTherapist {

  @LLMContextField(label = "Chatbot ID", order = 1)
  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Chatbot name", order = 2)
  @Column(nullable = false)
  private String chatbotName;

  private String chatbotIcon;

  @LLMContextField(label = "Chatbot role", order = 3)
  private String chatbotRole;

  @LLMContextField(label = "Chatbot tone", order = 4)
  private String chatbotTone;

  @LLMContextField(label = "Chatbot welcome message", order = 5)
  private String welcomeMessage;

  @LLMContextField(label = "Chatbot is active", order = 6)
  @Column(name = "is_active")
  private boolean isActive = false;

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

  public String toLLMContext() {
    StringBuilder sb = new StringBuilder(LLMContextBuilder.build(this));

    if (!this.chatbotTemplateDocuments.isEmpty()) {
      sb.append("\n--- Chatbot documents ---\n");
      for (ChatbotTemplateDocument document : this.chatbotTemplateDocuments) {
        sb.append(document.toLLMContext());
      }
    }

    return sb.toString();
  }
}
