package ch.uzh.ifi.imrg.platform.rest.dto.input;

public class CreatePatientDTO {

  private String name;

  public CreatePatientDTO() {}

  public CreatePatientDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
