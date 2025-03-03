package ch.uzh.ifi.imrg.platform.rest.dto.input;

public class CreatePatientDTO {
  private String name;
  private String gender;
  private int age;
  private String phoneNumber;
  private String email;
  private String address;
  private String description;

  public CreatePatientDTO() {}

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
}
