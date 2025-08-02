package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextField;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Table(name = "chatbot_template_documents")
public class ChatbotTemplateDocument implements OwnedByTherapist, HasLLMContext {

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

  @LLMContextField(label = "Chabot Document file name", order = 1)
  @Column(nullable = false)
  @Lob
  private String fileName;

  @LLMContextField(label = "Chabot Document file type", order = 2)
  @Column(nullable = false)
  @Lob
  private String fileType;

  @Lob
  @Column(nullable = false)
  private byte[] fileData;

  @LLMContextField(label = "Chabot Document extracted text", order = 3)
  @Lob
  @Column
  private String extractedText;

  @Override
  public String getOwningTherapistId() {
    return this.getChatbotTemplate().getTherapist().getId();
  }

  @Override
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);
    return sb.toString();
  }
}
