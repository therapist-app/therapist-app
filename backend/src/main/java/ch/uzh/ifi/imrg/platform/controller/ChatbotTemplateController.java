package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatbotTemplateInputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.DTOMapper;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.service.ChatbotTemplateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/therapists/{therapistId}/chatbot-templates")
public class ChatbotTemplateController {

    private final ChatbotTemplateService chatbotTemplateService;

    ChatbotTemplateController(ChatbotTemplateService chatbotTemplateService) {
        this.chatbotTemplateService = chatbotTemplateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatbotTemplateOutputDTO createTemplate(
            @PathVariable String therapistId,
            @RequestBody ChatbotTemplateInputDTO templateInputDTO) {
        ChatbotTemplate template = DTOMapper.INSTANCE.convertChatbotTemplateInputDTOtoEntity(templateInputDTO);
        ChatbotTemplate createdTemplate = chatbotTemplateService.createTemplate(therapistId, template);
        return DTOMapper.INSTANCE.convertEntityToChatbotTemplateOutputDTO(createdTemplate);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChatbotTemplateOutputDTO> getTemplatesByTherapist(@PathVariable String therapistId) {
        List<ChatbotTemplate> templates = chatbotTemplateService.getTemplatesByTherapist(therapistId);
        return templates.stream()
                .map(DTOMapper.INSTANCE::convertEntityToChatbotTemplateOutputDTO)
                .toList();
    }

    @PutMapping("/{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatbotTemplateOutputDTO updateTemplate(
            @PathVariable String therapistId,
            @PathVariable String templateId,
            @RequestBody ChatbotTemplateInputDTO templateInputDTO) {
        ChatbotTemplate template = DTOMapper.INSTANCE.convertChatbotTemplateInputDTOtoEntity(templateInputDTO);
        ChatbotTemplate updatedTemplate = chatbotTemplateService.updateTemplate(therapistId, templateId, template);
        return DTOMapper.INSTANCE.convertEntityToChatbotTemplateOutputDTO(updatedTemplate);
    }

    @DeleteMapping("/{templateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTemplate(
            @PathVariable String therapistId,
            @PathVariable String templateId) {
        chatbotTemplateService.deleteTemplate(therapistId, templateId);
    }

    @PostMapping("/{templateId}/clone")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatbotTemplateOutputDTO cloneTemplate(
            @PathVariable String therapistId,
            @PathVariable String templateId) {
        ChatbotTemplate clonedTemplate = chatbotTemplateService.cloneTemplate(therapistId, templateId);
        return DTOMapper.INSTANCE.convertEntityToChatbotTemplateOutputDTO(clonedTemplate);
    }
}