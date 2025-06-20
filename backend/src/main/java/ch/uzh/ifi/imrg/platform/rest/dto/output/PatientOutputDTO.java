package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class PatientOutputDTO {

  private String id;
  private String name;
  private Instant createdAt;
  private Instant updatedAt;
  private String phoneNumber;
  private String address;
  private String gender;
  private String description;
  private int age;
  private String email;
  private String maritalStatus;
  private String religion;
  private String education;
  private String occupation;
  private String income;
  private String dateOfAdmission;
  private List<TherapySessionOutputDTO> therapySessionsOutputDTO;
  private List<MeetingOutputDTO> meetingsOutputDTO;

  public PatientOutputDTO() {}

  public PatientOutputDTO(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
