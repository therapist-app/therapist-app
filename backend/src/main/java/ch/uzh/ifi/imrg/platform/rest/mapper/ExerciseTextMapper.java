package ch.uzh.ifi.imrg.platform.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ch.uzh.ifi.imrg.platform.entity.ExerciseText;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseTextOutputDTO;

@Mapper
public interface ExerciseTextMapper {
    ExerciseTextMapper INSTANCE = Mappers.getMapper(ExerciseTextMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "orderNumber", target = "orderNumber")
    ExerciseTextOutputDTO convertEntityToExerciseTextOutputDTO(ExerciseText exerciseText);
}
