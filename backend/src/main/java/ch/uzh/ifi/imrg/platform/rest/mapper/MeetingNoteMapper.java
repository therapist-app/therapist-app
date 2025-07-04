package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.MeetingNote;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingNoteOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MeetingNoteMapper {
  MeetingNoteMapper INSTANCE = Mappers.getMapper(MeetingNoteMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "title", target = "title")
  @Mapping(source = "content", target = "content")
  MeetingNoteOutputDTO convertEntityToMeetingNoteOutputDTO(MeetingNote meetingNote);
}
