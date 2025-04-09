package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreatePatientDTO {
  private String name;
  private String gender;
  private int age;
  private String phoneNumber;
  private String email;
  private String address;
  private String description;

  public CreatePatientDTO() {
  }

  public CreatePatientDTO(
      String name,
      String gender,
      int age,
      String phoneNumber,
      String email,
      String address,
      String description) {
    this.name = name;
    this.gender = gender;
    this.age = age;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.address = address;
    this.description = description;
  }
}
