package ch.uzh.ifi.imrg.platform.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ch.uzh.ifi.imrg.platform.entity.ExerciseFile;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseFileOutputDTO;

@Mapper
public interface ExerciseFileMapper {
    ExerciseFileMapper INSTANCE = Mappers.getMapper(ExerciseFileMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fileName", target = "fileName")
    @Mapping(source = "fileType", target = "fileType")
    @Mapping(source = "extractedText", target = "extractedText")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "orderNumber", target = "orderNumber")
    ExerciseFileOutputDTO convertEntityToExerciseFileOutputDTO(ExerciseFile exerciseFile);
}
