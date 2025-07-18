package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientDocumentOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientDocumentMapper {

  PatientDocumentMapper INSTANCE = Mappers.getMapper(PatientDocumentMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "isSharedWithPatient", target = "isSharedWithPatient")
  @Mapping(source = "fileName", target = "fileName")
  @Mapping(source = "fileType", target = "fileType")
  PatientDocumentOutputDTO convertEntityToPatientDocumentOutputDTO(PatientDocument patientDocument);
}
