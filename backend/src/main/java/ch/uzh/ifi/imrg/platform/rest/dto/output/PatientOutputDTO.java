package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientOutputDTO {

  private String id;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String phoneNumber;
  private String address;
  private String gender;
  private String description;
  private int age;
  private String email;
  private List<TherapySessionOutputDTO> therapySessionsOutputDTO;

  public PatientOutputDTO() {}

  public PatientOutputDTO(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
