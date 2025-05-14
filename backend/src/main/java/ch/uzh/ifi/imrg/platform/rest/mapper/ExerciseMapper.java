package ch.uzh.ifi.imrg.platform.rest.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseFile;
import ch.uzh.ifi.imrg.platform.entity.ExerciseText;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseFileOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseTextOutputDTO;

@Mapper
public interface ExerciseMapper {
    ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "exerciseType", target = "exerciseType")
    @Mapping(source = "exerciseTexts", target = "exerciseTextsOutputDTO")
    @Mapping(source = "exerciseFiles", target = "exerciseFilesOutputDTO")
    ExerciseOutputDTO convertEntityToExerciseOutputDTO(Exercise exercise);

    default List<ExerciseTextOutputDTO> mapExerciseTexts(
            List<ExerciseText> exerciseTexts) {
        if (exerciseTexts == null) {
            return null;
        }
        List<ExerciseTextOutputDTO> exerciseTextOutputDTOs = exerciseTexts.stream()
                .map(ExerciseTextMapper.INSTANCE::convertEntityToExerciseTextOutputDTO).collect(Collectors.toList());
        return exerciseTextOutputDTOs;
    }

    default List<ExerciseFileOutputDTO> mapExerciseFiles(
            List<ExerciseFile> exerciseFiles) {
        if (exerciseFiles == null) {
            return null;
        }
        List<ExerciseFileOutputDTO> exerciseFileOutputDTOs = exerciseFiles.stream()
                .map(ExerciseFileMapper.INSTANCE::convertEntityToExerciseFileOutputDTO).collect(Collectors.toList());
        return exerciseFileOutputDTOs;
    }

}
