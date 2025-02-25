package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.TherapistInputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    Therapist convertTherapistInputDTOtoEntity(TherapistInputDTO therapistDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "workspaceId", target = "workspaceId")
    TherapistOutputDTO convertEntityToTherapistOutputDTO(Therapist therapist);

}
