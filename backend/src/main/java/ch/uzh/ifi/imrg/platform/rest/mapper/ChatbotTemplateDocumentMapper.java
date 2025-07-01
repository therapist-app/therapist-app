package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateDocumentOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatbotTemplateDocumentMapper {

  ChatbotTemplateDocumentMapper INSTANCE = Mappers.getMapper(ChatbotTemplateDocumentMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "fileName", target = "fileName")
  @Mapping(source = "fileType", target = "fileType")
  ChatbotTemplateDocumentOutputDTO convertEntityToChatbotTemplateDocumentOutputDTO(
      ChatbotTemplateDocument chatbotTemplateDocument);
}
