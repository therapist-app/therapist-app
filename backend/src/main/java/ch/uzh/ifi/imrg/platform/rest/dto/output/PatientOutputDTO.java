package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.rest.dto.input.ComplaintDTO;
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
  private int age;
  private String email;
  private String initialPassword;
  private String maritalStatus;
  private String religion;
  private String education;
  private String occupation;
  private String income;
  private String dateOfAdmission;

  private List<ComplaintDTO> complaints;

  private String treatmentPast;
  private String treatmentCurrent;
  private String pastMedical;
  private String pastPsych;

  private String familyIllness;
  private String familySocial;

  private String personalPerinatal;
  private String personalChildhood;
  private String personalEducation;
  private String personalPlay;
  private String personalAdolescence;
  private String personalPuberty;
  private String personalObstetric;
  private String personalOccupational;
  private String personalMarital;
  private String personalPremorbid;

  private List<MeetingOutputDTO> meetingsOutputDTO;

  public PatientOutputDTO() {
  }

  public PatientOutputDTO(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
