package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateChatbotTemplateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatbotTemplateMapper {

  ChatbotTemplateMapper INSTANCE = Mappers.getMapper(ChatbotTemplateMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "chatbotName", target = "chatbotName")
  @Mapping(source = "chatbotIcon", target = "chatbotIcon")
  @Mapping(source = "chatbotRole", target = "chatbotRole")
  @Mapping(source = "chatbotTone", target = "chatbotTone")
  @Mapping(source = "welcomeMessage", target = "welcomeMessage")
  @Mapping(source = "active", target = "isActive")
  @Mapping(
      target = "patientId",
      expression =
          "java( template.getPatient()   != null ? template.getPatient().getId()   : null )")
  @Mapping(
      target = "therapistId",
      expression =
          "java( template.getTherapist() != null ? template.getTherapist().getId() : null )")
  ChatbotTemplateOutputDTO convertEntityToChatbotTemplateOutputDTO(ChatbotTemplate template);

  @Mapping(source = "chatbotName", target = "chatbotName")
  @Mapping(source = "chatbotIcon", target = "chatbotIcon")
  @Mapping(source = "chatbotRole", target = "chatbotRole")
  @Mapping(source = "chatbotTone", target = "chatbotTone")
  @Mapping(source = "welcomeMessage", target = "welcomeMessage")
  @Mapping(source = "isActive", target = "active")
  @Mapping(target = "chatbotTemplateDocuments", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "patient", ignore = true)
  @Mapping(target = "therapist", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  ChatbotTemplate convertCreateChatbotTemplateDTOtoEntity(CreateChatbotTemplateDTO templateDTO);
}
