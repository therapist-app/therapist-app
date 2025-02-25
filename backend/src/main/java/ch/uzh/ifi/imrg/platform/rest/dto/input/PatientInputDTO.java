package ch.uzh.ifi.imrg.platform.rest.dto.input;

public class PatientInputDTO {

    private String name;

    public PatientInputDTO() {
    }

    public PatientInputDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}