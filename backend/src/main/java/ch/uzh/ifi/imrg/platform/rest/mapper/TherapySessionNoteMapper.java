package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.TherapySessionNote;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapySessionNoteOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TherapySessionNoteMapper {
  TherapySessionNoteMapper INSTANCE = Mappers.getMapper(TherapySessionNoteMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "title", target = "title")
  @Mapping(source = "content", target = "content")
  TherapySessionNoteOutputDTO convertEntityToTherapySessionNoteOutputDTO(
      TherapySessionNote therapySessionNote);
}
