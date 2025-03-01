package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateChatbotTemplateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ChatbotTemplateMapper;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.service.ChatbotTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/therapists/{therapistId}/chatbot-templates")
public class ChatbotTemplateController {

  private final ChatbotTemplateService chatbotTemplateService;

  ChatbotTemplateController(ChatbotTemplateService chatbotTemplateService) {
    this.chatbotTemplateService = chatbotTemplateService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO createTemplate(
      @PathVariable String therapistId, @RequestBody CreateChatbotTemplateDTO templateInputDTO) {
    ChatbotTemplate template = ChatbotTemplateMapper.INSTANCE.convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);
    Therapist updatedTherapist = chatbotTemplateService.createTemplate(therapistId, template);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
  }

  @PutMapping("/{templateId}")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO updateTemplate(
      @PathVariable String therapistId,
      @PathVariable String templateId,
      @RequestBody CreateChatbotTemplateDTO templateInputDTO) {
    ChatbotTemplate template = ChatbotTemplateMapper.INSTANCE.convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);
    Therapist updatedTherapist = chatbotTemplateService.updateTemplate(therapistId, templateId, template);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
  }

  @DeleteMapping("/{templateId}")
  @ResponseStatus(HttpStatus.OK)
  public TherapistOutputDTO deleteTemplate(@PathVariable String therapistId, @PathVariable String templateId) {
    Therapist updatedTherapist = chatbotTemplateService.deleteTemplate(therapistId, templateId);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
  }

  @PostMapping("/{templateId}/clone")
  @ResponseStatus(HttpStatus.CREATED)
  public TherapistOutputDTO cloneTemplate(
      @PathVariable String therapistId, @PathVariable String templateId) {
    Therapist updatedTherapist = chatbotTemplateService.cloneTemplate(therapistId, templateId);
    return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
  }
}
