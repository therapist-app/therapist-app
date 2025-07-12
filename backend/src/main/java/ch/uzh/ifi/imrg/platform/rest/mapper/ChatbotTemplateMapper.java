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
  @Mapping(source = "chatbotVoice", target = "chatbotVoice")
  @Mapping(source = "chatbotGender", target = "chatbotGender")
  @Mapping(source = "preConfiguredExercise", target = "preConfiguredExercise")
  @Mapping(source = "additionalExercise", target = "additionalExercise")
  @Mapping(source = "animation", target = "animation")
  @Mapping(source = "chatbotInputPlaceholder", target = "chatbotInputPlaceholder")
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
  @Mapping(source = "description", target = "description")
  @Mapping(source = "chatbotModel", target = "chatbotModel")
  @Mapping(source = "chatbotIcon", target = "chatbotIcon")
  @Mapping(source = "chatbotLanguage", target = "chatbotLanguage")
  @Mapping(source = "chatbotRole", target = "chatbotRole")
  @Mapping(source = "chatbotTone", target = "chatbotTone")
  @Mapping(source = "welcomeMessage", target = "welcomeMessage")
  @Mapping(source = "chatbotVoice", target = "chatbotVoice")
  @Mapping(source = "chatbotGender", target = "chatbotGender")
  @Mapping(source = "preConfiguredExercise", target = "preConfiguredExercise")
  @Mapping(source = "additionalExercise", target = "additionalExercise")
  @Mapping(source = "animation", target = "animation")
  @Mapping(source = "chatbotInputPlaceholder", target = "chatbotInputPlaceholder")
  @Mapping(target = "chatbotTemplateDocuments", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "patient", ignore = true)
  @Mapping(target = "therapist", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  ChatbotTemplate convertCreateChatbotTemplateDTOtoEntity(CreateChatbotTemplateDTO templateDTO);
}
