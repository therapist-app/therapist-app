package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.TherapySession;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapySessionOutputDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TherapySessionMapper {

    TherapySessionMapper INSTANCE = Mappers.getMapper(TherapySessionMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sessionStart", target = "sessionStart")
    @Mapping(source = "sessionEnd", target = "sessionEnd")
    TherapySessionOutputDTO convertEntityToSessionOutputDTO(TherapySession session);

}
