package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "therapists")
public class Therapist implements Serializable {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(unique = true)
  private String workspaceId = UUID.randomUUID().toString();

  @OneToMany(mappedBy = "therapist", fetch = FetchType.EAGER)
  private List<Patient> patients = new ArrayList<>();

  @OneToMany(mappedBy = "therapist", fetch = FetchType.EAGER)
  private List<ChatbotTemplate> chatbotTemplates = new ArrayList<>();
}
