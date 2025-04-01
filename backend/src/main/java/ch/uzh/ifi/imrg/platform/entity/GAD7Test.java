package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "gad7_tests")
public class GAD7Test {

    @Id
    @Column(name = "test_id", unique = true)
    private String testId = UUID.randomUUID().toString();

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    // Associations to Patient and TherapySession
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private TherapySession therapySession;

    @Column(nullable = false)
    private int question1;
    @Column(nullable = false)
    private int question2;
    @Column(nullable = false)
    private int question3;
    @Column(nullable = false)
    private int question4;
    @Column(nullable = false)
    private int question5;
    @Column(nullable = false)
    private int question6;
    @Column(nullable = false)
    private int question7;

    // Getters and setters
    public String getTestId() {
        return testId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public TherapySession getTherapySession() {
        return therapySession;
    }

    public void setTherapySession(TherapySession therapySession) {
        this.therapySession = therapySession;
    }

    public int getQuestion1() {
        return question1;
    }

    public void setQuestion1(int question1) {
        this.question1 = question1;
    }

    public int getQuestion2() {
        return question2;
    }

    public void setQuestion2(int question2) {
        this.question2 = question2;
    }

    public int getQuestion3() {
        return question3;
    }

    public void setQuestion3(int question3) {
        this.question3 = question3;
    }

    public int getQuestion4() {
        return question4;
    }

    public void setQuestion4(int question4) {
        this.question4 = question4;
    }

    public int getQuestion5() {
        return question5;
    }

    public void setQuestion5(int question5) {
        this.question5 = question5;
    }

    public int getQuestion6() {
        return question6;
    }

    public void setQuestion6(int question6) {
        this.question6 = question6;
    }

    public int getQuestion7() {
        return question7;
    }

    public void setQuestion7(int question7) {
        this.question7 = question7;
    }
}