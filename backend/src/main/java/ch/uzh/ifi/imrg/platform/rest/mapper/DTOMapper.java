package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateChatbotTemplateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    Therapist convertCreateTherapistDTOtoEntity(CreateTherapistDTO therapistDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "workspaceId", target = "workspaceId")
    TherapistOutputDTO convertEntityToTherapistOutputDTO(Therapist therapist);

    @Mapping(source = "chatbotName", target = "chatbotName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "chatbotModel", target = "chatbotModel")
    @Mapping(source = "chatbotIcon", target = "chatbotIcon")
    @Mapping(source = "chatbotLanguage", target = "chatbotLanguage")
    @Mapping(source = "chatbotRole", target = "chatbotRole")
    @Mapping(source = "chatbotTone", target = "chatbotTone")
    @Mapping(source = "welcomeMessage", target = "welcomeMessage")
    @Mapping(source = "workspaceId", target = "workspaceId")
    ChatbotTemplate convertCreateChatbotTemplateDTOtoEntity(CreateChatbotTemplateDTO templateDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "chatbotName", target = "chatbotName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "chatbotModel", target = "chatbotModel")
    @Mapping(source = "chatbotIcon", target = "chatbotIcon")
    @Mapping(source = "chatbotLanguage", target = "chatbotLanguage")
    @Mapping(source = "chatbotRole", target = "chatbotRole")
    @Mapping(source = "chatbotTone", target = "chatbotTone")
    @Mapping(source = "welcomeMessage", target = "welcomeMessage")
    @Mapping(source = "workspaceId", target = "workspaceId")
    ChatbotTemplateOutputDTO convertEntityToChatbotTemplateOutputDTO(ChatbotTemplate template);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    PatientOutputDTO convertEntityToPatientOutputDTO(Patient patient);

    @Mapping(source = "name", target = "name")
    Patient convertCreatePatientDtoToEntity(CreatePatientDTO createPatientDTO);

}
