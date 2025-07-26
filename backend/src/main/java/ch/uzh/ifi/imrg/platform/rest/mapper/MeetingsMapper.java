package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.MeetingNote;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MeetingsMapper {

  MeetingsMapper INSTANCE = Mappers.getMapper(MeetingsMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "meetingStart", target = "meetingStart")
  @Mapping(source = "meetingEnd", target = "meetingEnd")
  @Mapping(source = "meetingNotes", target = "meetingNotesOutputDTO")
  @Mapping(source = "location", target = "location")
  @Mapping(source = "meetingStatus", target = "meetingStatus")
  MeetingOutputDTO convertEntityToMeetingOutputDTO(Meeting meeting);

  default List<MeetingNoteOutputDTO> mapMeetingNotes(List<MeetingNote> meetingNotes) {
    if (meetingNotes == null) {
      return null;
    }
    List<MeetingNoteOutputDTO> meetingNotesOutputDTO =
        meetingNotes.stream()
            .map(MeetingNoteMapper.INSTANCE::convertEntityToMeetingNoteOutputDTO)
            .collect(Collectors.toList());

    return meetingNotesOutputDTO;
  }
}
