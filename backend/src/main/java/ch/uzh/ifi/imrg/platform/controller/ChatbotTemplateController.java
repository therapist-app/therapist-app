package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateChatbotTemplateDTO;
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
@RequestMapping("/therapists/chatbot-templates")
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
    public TherapistOutputDTO createTemplate(
            @RequestBody CreateChatbotTemplateDTO templateInputDTO,
            HttpServletRequest httpServletRequest) {
        logger.info("/therapists/chatbot-templates");
        Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

        ChatbotTemplate template = ChatbotTemplateMapper.INSTANCE
                .convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);
        Therapist updatedTherapist = chatbotTemplateService.createTemplate(loggedInTherapist.getId(), template);
        return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
    }

    @PutMapping("/{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public TherapistOutputDTO updateTemplate(
            @PathVariable String templateId,
            @RequestBody CreateChatbotTemplateDTO templateInputDTO,
            HttpServletRequest httpServletRequest) {
        logger.info("/therapists/chatbot-templates/" + templateId);
        Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

        ChatbotTemplate template = ChatbotTemplateMapper.INSTANCE
                .convertCreateChatbotTemplateDTOtoEntity(templateInputDTO);
        Therapist updatedTherapist = chatbotTemplateService.updateTemplate(loggedInTherapist.getId(), templateId,
                template);
        return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
    }

    @DeleteMapping("/{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public TherapistOutputDTO deleteTemplate(
            @PathVariable String templateId, HttpServletRequest httpServletRequest) {
        logger.info("/therapists/chatbot-templates/" + templateId);
        Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

        Therapist updatedTherapist = chatbotTemplateService.deleteTemplate(loggedInTherapist.getId(), templateId);
        return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
    }

    @PostMapping("/{templateId}/clone")
    @ResponseStatus(HttpStatus.CREATED)
    public TherapistOutputDTO cloneTemplate(
            @PathVariable String templateId, HttpServletRequest httpServletRequest) {
        logger.info("/therapists/chatbot-templates/" + templateId + "/clone");
        Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);

        Therapist updatedTherapist = chatbotTemplateService.cloneTemplate(loggedInTherapist.getId(), templateId);
        return TherapistMapper.INSTANCE.convertEntityToTherapistOutputDTO(updatedTherapist).sortDTO();
    }
}
