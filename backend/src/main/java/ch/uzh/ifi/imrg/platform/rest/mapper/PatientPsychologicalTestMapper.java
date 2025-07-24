package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.generated.model.PsychologicalTestOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.PsychologicalTestQuestionOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestQuestionOutputDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientPsychologicalTestMapper {

  PatientPsychologicalTestMapper INSTANCE = Mappers.getMapper(PatientPsychologicalTestMapper.class);

  @Mapping(target = "id", ignore = true) // No id field in source, so we ignore it
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "patientId", source = "patientId")
  @Mapping(target = "completedAt", source = "completedAt")
  @Mapping(target = "questions", expression = "java(mapQuestions(apiDTO.getQuestions()))")
  PsychologicalTestOutputDTO toPsychologicalTestOutputDTO(
      PsychologicalTestOutputDTOPatientAPI apiDTO);

  default List<PsychologicalTestQuestionOutputDTO> mapQuestions(
      List<PsychologicalTestQuestionOutputDTOPatientAPI> questions) {
    if (questions == null) {
      return null;
    }
    return questions.stream()
        .map(
            question -> {
              PsychologicalTestQuestionOutputDTO dto = new PsychologicalTestQuestionOutputDTO();
              dto.setQuestion(question.getQuestion());
              dto.setScore(question.getScore());
              return dto;
            })
        .collect(Collectors.toList());
  }
}
