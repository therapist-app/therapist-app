package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.entity.TherapySessionNote;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapySessionNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapySessionOutputDTO;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TherapySessionMapper {

  TherapySessionMapper INSTANCE = Mappers.getMapper(TherapySessionMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "sessionStart", target = "sessionStart")
  @Mapping(source = "sessionEnd", target = "sessionEnd")
  @Mapping(source = "therapySessionNotes", target = "therapySessionNotesOutputDTO")
  TherapySessionOutputDTO convertEntityToSessionOutputDTO(TherapySession session);

  default List<TherapySessionNoteOutputDTO> mapTherapySessionsNotes(
      List<TherapySessionNote> therapySessionNotes) {
    if (therapySessionNotes == null) {
      return null;
    }
    List<TherapySessionNoteOutputDTO> therapySessionNotesOutputDTO =
        therapySessionNotes.stream()
            .map(TherapySessionNoteMapper.INSTANCE::convertEntityToTherapySessionNoteOutputDTO)
            .collect(Collectors.toList());

    therapySessionNotesOutputDTO.sort(
        Comparator.comparing(
            TherapySessionNoteOutputDTO::getCreatedAt,
            Comparator.nullsLast(Comparator.reverseOrder())));

    return therapySessionNotesOutputDTO;
  }
}
