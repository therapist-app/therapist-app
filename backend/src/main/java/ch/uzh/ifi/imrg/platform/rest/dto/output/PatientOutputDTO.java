package ch.uzh.ifi.imrg.platform.rest.dto.output;

public class PatientOutputDTO {

    private String id;
    private String name;

    public PatientOutputDTO() {
    }

    public PatientOutputDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}