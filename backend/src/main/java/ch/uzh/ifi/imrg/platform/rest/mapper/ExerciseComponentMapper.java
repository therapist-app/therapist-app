package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseComponentOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExerciseComponentMapper {
  ExerciseComponentMapper INSTANCE = Mappers.getMapper(ExerciseComponentMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "description", target = "description")
  @Mapping(source = "exerciseComponentType", target = "exerciseComponentType")
  @Mapping(source = "fileName", target = "fileName")
  @Mapping(source = "fileType", target = "fileType")
  @Mapping(source = "extractedText", target = "extractedText")
  @Mapping(source = "orderNumber", target = "orderNumber")
  ExerciseComponentOutputDTO convertEntityToExerciseComponentOutputDTO(
      ExerciseComponent exerciseComponent);
}
