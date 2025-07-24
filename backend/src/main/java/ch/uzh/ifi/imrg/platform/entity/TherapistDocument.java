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
@Table(name = "therapist_documents")
public class TherapistDocument implements OwnedByTherapist, HasLLMContext {

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
  @JoinColumn(name = "therapist_id")
  private Therapist therapist;

  @LLMContextField(label = "Coach Document file name", order = 1)
  @Column(nullable = false)
  private String fileName;

  @LLMContextField(label = "Coach Document file type", order = 2)
  @Column(nullable = false)
  private String fileType;

  @Lob
  @Column(nullable = false)
  private byte[] fileData;

  @LLMContextField(label = "Coach Document extracted text", order = 3)
  @Lob
  @Column
  private String extractedText;

  @Override
  public String getOwningTherapistId() {
    return this.getTherapist().getId();
  }

  @Override
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);
    return sb.toString();
  }
}
