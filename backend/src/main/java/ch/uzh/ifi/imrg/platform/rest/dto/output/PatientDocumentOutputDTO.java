package ch.uzh.ifi.imrg.platform.rest.dto.output;

import lombok.Data;

@Data
public class PatientDocumentOutputDTO {
  private String id;
  private Boolean isSharedWithPatient;
  private String fileName;
  private String fileType;
}
