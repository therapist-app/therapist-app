package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePatientDTO {
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

  public CreatePatientDTO() {}

  public CreatePatientDTO(
      String name,
      String gender,
      int age,
      String phoneNumber,
      String email,
      String address,
      String maritalStatus,
      String religion,
      String education,
      String occupation,
      String income,
      String dateOfAdmission) {
    this.name = name;
    this.gender = gender;
    this.age = age;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.address = address;
    this.maritalStatus = maritalStatus;
    this.religion = religion;
    this.education = education;
    this.occupation = occupation;
    this.income = income;
    this.dateOfAdmission = dateOfAdmission
  }
}
