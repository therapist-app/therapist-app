package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateChatbotTemplateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ChatbotTemplateMapper;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.ChatbotTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot-templates")
public class ChatbotTemplateController {

  private final ChatbotTemplateService chatbotTemplateService;

  ChatbotTemplateController(ChatbotTemplateService chatbotTemplateService) {
    this.chatbotTemplateService = chatbotTemplateService;
  }

  @GetMapping("/{templateId}")
  @ResponseStatus(HttpStatus.OK)
  public ChatbotTemplateOutputDTO getTemplate(
      @PathVariable String templateId, @CurrentTherapistId String therapistId) {

    return chatbotTemplateService.getTemplateForTherapist(templateId, therapistId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ChatbotTemplateOutputDTO createTemplate(
      @RequestBody CreateChatbotTemplateDTO templateInputDTO,
      @CurrentTherapistId String therapistId) {

    ChatbotTemplate template =
        ChatbotTemplateMapper.INSTANCE.convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);
    return chatbotTemplateService.createTemplate(template, therapistId);
  }

  @PostMapping("/patients/{patientId}")
  @ResponseStatus(HttpStatus.CREATED)
  public ChatbotTemplateOutputDTO createTemplateForPatient(
      @PathVariable String patientId,
      @RequestBody CreateChatbotTemplateDTO templateInputDTO,
      @CurrentTherapistId String therapistId) {

    ChatbotTemplate template =
        ChatbotTemplateMapper.INSTANCE.convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);

    return chatbotTemplateService.createTemplateForPatient(patientId, template, therapistId);
  }

  @PutMapping("/{templateId}")
  @ResponseStatus(HttpStatus.OK)
  public ChatbotTemplateOutputDTO updateTemplate(
      @PathVariable String templateId,
      @RequestBody CreateChatbotTemplateDTO templateInputDTO,
      @CurrentTherapistId String therapistId) {

    ChatbotTemplate template =
        ChatbotTemplateMapper.INSTANCE.convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);
    return chatbotTemplateService.updateTemplate(templateId, template, therapistId);
  }

  @DeleteMapping("/{templateId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTemplate(
      @PathVariable String templateId, @CurrentTherapistId String therapistId) {

    chatbotTemplateService.deleteTemplate(templateId, therapistId);
  }

  @DeleteMapping("/patients/{patientId}/{templateId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTemplateForPatient(
      @PathVariable String patientId,
      @PathVariable String templateId,
      @CurrentTherapistId String therapistId) {

    chatbotTemplateService.deleteTemplateForPatient(patientId, templateId, therapistId);
  }

  @PostMapping("/{templateId}/clone")
  @ResponseStatus(HttpStatus.CREATED)
  public ChatbotTemplateOutputDTO cloneTemplate(
      @PathVariable String templateId, @CurrentTherapistId String therapistId) {
    return chatbotTemplateService.cloneTemplate(templateId, therapistId);
  }

  @PostMapping("/patients/{patientId}/{templateId}/clone")
  @ResponseStatus(HttpStatus.CREATED)
  public ChatbotTemplateOutputDTO cloneTemplateForPatient(
      @PathVariable String patientId,
      @PathVariable String templateId,
      @CurrentTherapistId String therapistId) {

    return chatbotTemplateService.cloneTemplateForPatient(patientId, templateId, therapistId);
  }
}
