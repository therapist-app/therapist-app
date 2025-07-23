package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.util.List;
import lombok.Data;

@Data
public class UpdatePatientDetailDTO {
  private String id;
  private String name;
  private String gender;
  private int age;
  private String phoneNumber;
  private String email;
  private String address;
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

  public UpdatePatientDetailDTO() {}
}
