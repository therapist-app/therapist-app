package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CounselingPlanPhaseGoalMapper {
  CounselingPlanPhaseGoalMapper INSTANCE = Mappers.getMapper(CounselingPlanPhaseGoalMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "goalName", target = "goalName")
  @Mapping(source = "goalDescription", target = "goalDescription")
  CounselingPlanPhaseGoalOutputDTO convertEntityToCounselingPlanPhaseGoalOutputDTO(
      CounselingPlanPhaseGoal counselingPlanPhaseGoal);
}
