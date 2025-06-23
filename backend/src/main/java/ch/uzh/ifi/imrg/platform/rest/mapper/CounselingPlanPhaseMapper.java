package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CounselingPlanPhaseMapper {
  CounselingPlanPhaseMapper INSTANCE = Mappers.getMapper(CounselingPlanPhaseMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "phaseName", target = "phaseName")
  @Mapping(source = "startDate", target = "startDate")
  @Mapping(source = "endDate", target = "endDate")
  @Mapping(source = "phaseExercises", target = "phaseExercisesOutputDTO")
  @Mapping(source = "phaseGoals", target = "phaseGoalsOutputDTO")
  CounselingPlanPhaseOutputDTO convertEntityToCounselingPlanPhaseOutputDTO(
      CounselingPlanPhase counselingPlanPhase);

  default List<CounselingPlanPhaseGoalOutputDTO> mapPhaseGoals(
      List<CounselingPlanPhaseGoal> phaseGoals) {
    if (phaseGoals == null) {
      return null;
    }
    return phaseGoals.stream()
        .map(
            CounselingPlanPhaseGoalMapper.INSTANCE::convertEntityToCounselingPlanPhaseGoalOutputDTO)
        .collect(Collectors.toList());
  }

  default List<ExerciseOutputDTO> mapPhaseExercises(List<Exercise> phaseExercises) {
    if (phaseExercises == null) {
      return null;
    }
    return phaseExercises.stream()
        .map(ExerciseMapper.INSTANCE::convertEntityToExerciseOutputDTO)
        .collect(Collectors.toList());
  }
}
