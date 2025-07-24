package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.utils.LLMContextField;
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

  public static final Integer HIERARCHY_LEVEL = ChatbotTemplate.HIERARCHY_LEVEL + 1;

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

  @LLMContextField(label = "Chabot Document file name", order = 2)
  @Column(nullable = false)
  private String fileName;

  @LLMContextField(label = "Chabot Document file type", order = 3)
  @Column(nullable = false)
  private String fileType;

  @Lob
  @Column(nullable = false)
  private byte[] fileData;

  @LLMContextField(label = "Chabot Document extracted text", order = 4)
  @Lob
  @Column
  private String extractedText;

  @Override
  public String getOwningTherapistId() {
    return this.getChatbotTemplate().getTherapist().getId();
  }

  public String toLLMContext() {
    return FormatUtil.indentBlock(LLMContextBuilder.build(this), HIERARCHY_LEVEL, false);
  }
}
