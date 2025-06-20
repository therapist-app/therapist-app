package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistDocumentOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TherapistDocumentMapper {

  TherapistDocumentMapper INSTANCE = Mappers.getMapper(TherapistDocumentMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "fileName", target = "fileName")
  @Mapping(source = "fileType", target = "fileType")
  TherapistDocumentOutputDTO convertEntityToTherapistDocumentOutputDTO(
      TherapistDocument therapistDocument);
}
