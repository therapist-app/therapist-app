package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "patients")
public class Patient implements Serializable {

  @Id
  @Column(unique = true)
  private String id = UUID.randomUUID().toString();

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String gender;

  @Column(nullable = false)
  private int age;

  @Column(nullable = false, unique = true)
  private String phoneNumber;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String address;

  @Column(nullable = true)
  private String description;

  @ManyToOne
  @JoinColumn(name = "therapist_id", referencedColumnName = "id")
  private Therapist therapist;

  @Column(unique = true)
  private String workspaceId = UUID.randomUUID().toString();

  @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
  private List<TherapySession> sessions = new ArrayList<>();

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Therapist getTherapist() {
    return therapist;
  }

  public void setTherapist(Therapist therapist) {
    this.therapist = therapist;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public List<TherapySession> getSessions() {
    return sessions;
  }

  public void setSessions(List<TherapySession> sessions) {
    this.sessions = sessions;
  }
}
