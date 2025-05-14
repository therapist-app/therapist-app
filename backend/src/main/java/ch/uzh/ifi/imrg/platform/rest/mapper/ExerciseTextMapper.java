package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.ExerciseText;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseTextOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExerciseTextMapper {
  ExerciseTextMapper INSTANCE = Mappers.getMapper(ExerciseTextMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "text", target = "text")
  @Mapping(source = "orderNumber", target = "orderNumber")
  ExerciseTextOutputDTO convertEntityToExerciseTextOutputDTO(ExerciseText exerciseText);
}
