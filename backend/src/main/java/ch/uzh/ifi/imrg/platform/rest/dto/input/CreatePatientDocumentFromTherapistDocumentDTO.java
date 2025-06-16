package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreatePatientDocumentFromTherapistDocumentDTO {
    private String therapistDocumentId;
    private String patientId;

}
