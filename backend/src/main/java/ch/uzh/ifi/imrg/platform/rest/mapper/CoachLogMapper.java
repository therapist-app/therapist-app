package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.generated.model.LogOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.rest.dto.output.LogOutputDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CoachLogMapper {

  CoachLogMapper INSTANCE = Mappers.getMapper(CoachLogMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "patientId", target = "patientId")
  @Mapping(source = "logType", target = "logType")
  @Mapping(source = "timestamp", target = "timestamp")
  @Mapping(source = "associatedEntityId", target = "associatedEntityId")
  @Mapping(source = "comment", target = "comment")
  LogOutputDTO apiToLocal(LogOutputDTOPatientAPI apiDto);

  default List<LogOutputDTO> apiToLocal(List<LogOutputDTOPatientAPI> apiDtos) {
    if (apiDtos == null) {
      return null;
    }
    return apiDtos.stream().map(this::apiToLocal).toList();
  }
}
