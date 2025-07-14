package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Table(name = "chatbot_template_documents")
public class ChatbotTemplateDocument implements OwnedByTherapist {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @ManyToOne
  @JoinColumn(name = "chatbot_template_id", nullable = false)
  private ChatbotTemplate chatbotTemplate;

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private String fileType;

  @Lob
  @Column(nullable = false)
  private byte[] fileData;

  @Lob
  @Column
  private String extractedText;

  @Override
  public String getOwningTherapistId() {
    return this.getChatbotTemplate().getTherapist().getId();
  }
}
