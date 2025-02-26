package ch.uzh.ifi.imrg.platform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "therapists")
public class Therapist implements Serializable {

    @Id
    @Column(unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String workspaceId = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "therapist", fetch = FetchType.LAZY)
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "therapist", fetch = FetchType.LAZY)
    private List<ChatbotTemplate> chatbotTemplates = new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public List<Patient> getPatients() {
        return patients;
    }
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<ChatbotTemplate> getChatbotTemplates() {
        return chatbotTemplates;
    }

    public void setChatbotTemplates(List<ChatbotTemplate> chatbotTemplates) {
        this.chatbotTemplates = chatbotTemplates;
    }
}