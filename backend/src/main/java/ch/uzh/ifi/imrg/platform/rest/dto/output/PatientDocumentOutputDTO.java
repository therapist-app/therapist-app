package ch.uzh.ifi.imrg.platform.rest.dto.output;

import lombok.Data;

@Data
public class PatientDocumentOutputDTO {
  private String id;
  private String fileName;
  private String fileType;
  private byte[] fileData;
}
