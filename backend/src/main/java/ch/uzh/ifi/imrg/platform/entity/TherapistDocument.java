package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.utils.FormatUtil;
import ch.uzh.ifi.imrg.platform.utils.LLMContextBuilder;
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

  public static final Integer HIERARCHY_LEVEL = Therapist.HIERARCHY_LEVEL + 1;

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

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private String fileType;

  @Lob
  @Column(nullable = false)
  private byte[] fileData;

  @Lob @Column private String extractedText;

  @Override
  public String getOwningTherapistId() {
    return this.getTherapist().getId();
  }

  @Override
  public String toLLMContext() {
    return FormatUtil.indentBlock(LLMContextBuilder.build(this), HIERARCHY_LEVEL, false);
  }
}
