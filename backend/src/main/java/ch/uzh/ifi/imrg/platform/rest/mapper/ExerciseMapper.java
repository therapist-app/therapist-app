package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseComponentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExerciseMapper {
  ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "exerciseTitle", target = "exerciseTitle")
  @Mapping(source = "exerciseDescription", target = "exerciseDescription")
  @Mapping(source = "exerciseExplanation", target = "exerciseExplanation")
  @Mapping(source = "exerciseStart", target = "exerciseStart")
  @Mapping(source = "exerciseEnd", target = "exerciseEnd")
  @Mapping(source = "isPaused", target = "isPaused")
  @Mapping(source = "exerciseComponents", target = "exerciseComponentsOutputDTO")
  ExerciseOutputDTO convertEntityToExerciseOutputDTO(Exercise exercise);

  default List<ExerciseComponentOutputDTO> mapExerciseComponents(
      List<ExerciseComponent> exerciseComponents) {
    if (exerciseComponents == null) {
      return null;
    }
    List<ExerciseComponentOutputDTO> exerciseComponentsOutputDTOs =
        exerciseComponents.stream()
            .map(ExerciseComponentMapper.INSTANCE::convertEntityToExerciseComponentOutputDTO)
            .sorted(
                Comparator.comparing(
                    ExerciseComponentOutputDTO::getOrderNumber,
                    Comparator.nullsLast(Comparator.naturalOrder())))
            .collect(Collectors.toList());
    return exerciseComponentsOutputDTOs;
  }
}
