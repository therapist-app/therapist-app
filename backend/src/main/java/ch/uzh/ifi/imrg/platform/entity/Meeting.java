package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextField;
import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
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
@Table(name = "meetings")
public class Meeting implements Serializable, OwnedByTherapist, HasLLMContext {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @LLMContextField(label = "Meeting start", order = 1)
  @Column(name = "meeting_start")
  private Instant meetingStart;

  @LLMContextField(label = "Meeting end", order = 2)
  @Column(name = "meeting_end")
  private Instant meetingEnd;

  @LLMContextField(label = "Meeting location", order = 3)
  @Column(nullable = true)
  private String location;

  @LLMContextField(label = "Meeting status", order = 4)
  @Column()
  private MeetingStatus meetingStatus;

  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  private Patient patient;

  @OneToMany(
      mappedBy = "meeting",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("createdAt DESC")
  private List<MeetingNote> meetingNotes = new ArrayList<>();

  @Override
  public String getOwningTherapistId() {
    return this.getPatient().getTherapist().getId();
  }

  @Override
  public String toLLMContext(Integer level) {
    StringBuilder sb = LLMContextBuilder.getOwnProperties(this, level);
    LLMContextBuilder.addLLMContextOfListOfEntities(sb, meetingNotes, "Meeting Note", level);
    return sb.toString();
  }
}
