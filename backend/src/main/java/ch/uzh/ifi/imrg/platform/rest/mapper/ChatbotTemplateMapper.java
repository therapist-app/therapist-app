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
  @Mapping(source = "description", target = "description")
  @Mapping(source = "chatbotModel", target = "chatbotModel")
  @Mapping(source = "chatbotIcon", target = "chatbotIcon")
  @Mapping(source = "chatbotLanguage", target = "chatbotLanguage")
  @Mapping(source = "chatbotRole", target = "chatbotRole")
  @Mapping(source = "chatbotTone", target = "chatbotTone")
  @Mapping(source = "welcomeMessage", target = "welcomeMessage")
  @Mapping(source = "workspaceId", target = "workspaceId")
  ChatbotTemplateOutputDTO convertEntityToChatbotTemplateOutputDTO(ChatbotTemplate template);

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
}
