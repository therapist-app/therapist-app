package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "patients")
public class Patient implements Serializable {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "therapist_id", referencedColumnName = "id")
  private Therapist therapist;

  @Column(unique = true)
  private String workspaceId = UUID.randomUUID().toString();

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Therapist getTherapist() {
    return therapist;
  }

  public void setTherapist(Therapist therapist) {
    this.therapist = therapist;
  }
}
