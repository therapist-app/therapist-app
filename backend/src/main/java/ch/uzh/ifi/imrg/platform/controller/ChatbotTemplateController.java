package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateChatbotTemplateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ChatbotTemplateMapper;
import ch.uzh.ifi.imrg.platform.rest.mapper.TherapistMapper;
import ch.uzh.ifi.imrg.platform.service.ChatbotTemplateService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot-templates")
public class ChatbotTemplateController {

    private final Logger logger = LoggerFactory.getLogger(ChatbotTemplateController.class);

    private final ChatbotTemplateService chatbotTemplateService;
    private final TherapistService therapistService;

    ChatbotTemplateController(
            ChatbotTemplateService chatbotTemplateService, TherapistService therapistService) {
        this.chatbotTemplateService = chatbotTemplateService;
        this.therapistService = therapistService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatbotTemplateOutputDTO createTemplate(
            @RequestBody CreateChatbotTemplateDTO templateInputDTO,
            HttpServletRequest httpServletRequest) {
        Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

        ChatbotTemplate template = ChatbotTemplateMapper.INSTANCE
                .convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);
        return chatbotTemplateService.createTemplate(loggedInTherapist.getId(), template);
    }

    @PutMapping("/{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatbotTemplateOutputDTO updateTemplate(
            @PathVariable String templateId,
            @RequestBody CreateChatbotTemplateDTO templateInputDTO,
            HttpServletRequest httpServletRequest) {
        Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

        ChatbotTemplate template = ChatbotTemplateMapper.INSTANCE
                .convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);
        return chatbotTemplateService.updateTemplate(loggedInTherapist.getId(), templateId, template);
    }

    @DeleteMapping("/{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTemplate(
            @PathVariable String templateId, HttpServletRequest httpServletRequest) {
        Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

        chatbotTemplateService.deleteTemplate(loggedInTherapist.getId(), templateId);
    }

    @PostMapping("/{templateId}/clone")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatbotTemplateOutputDTO cloneTemplate(
            @PathVariable String templateId, HttpServletRequest httpServletRequest) {
        Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

        return chatbotTemplateService.cloneTemplate(loggedInTherapist.getId(), templateId);
    }
}
