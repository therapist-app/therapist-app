package ch.uzh.ifi.imrg.platform.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "complaints")
public class Complaint {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String mainComplaint;
  private String duration;
  private String onset;
  private String course;
  private String precipitatingFactors;
  private String aggravatingRelieving;
  private String timeline;
  private String disturbances;
  private String suicidalIdeation;
  private String negativeHistory;

  @ManyToOne
  @JoinColumn(name = "patient_id")
  private Patient patient;
}
