package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CounselingPlanMapper {
  CounselingPlanMapper INSTANCE = Mappers.getMapper(CounselingPlanMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "createdAt", target = "createdAt")
  @Mapping(source = "updatedAt", target = "updatedAt")
  @Mapping(source = "counselingPlanPhases", target = "counselingPlanPhasesOutputDTO")
  CounselingPlanOutputDTO convertEntityToCounselingPlanOutputDTO(CounselingPlan counselingPlan);

  default List<CounselingPlanPhaseOutputDTO> mapCounselingPlanPhases(
      List<CounselingPlanPhase> counselingPlanPhases) {
    if (counselingPlanPhases == null) {
      return null;
    }
    return counselingPlanPhases.stream()
        .map(CounselingPlanPhaseMapper.INSTANCE::convertEntityToCounselingPlanPhaseOutputDTO)
        .collect(Collectors.toList());
  }
}
