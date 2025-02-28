package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatbotTemplateService {

  private final ChatbotTemplateRepository chatbotTemplateRepository;
  private final TherapistRepository therapistRepository;

  @Autowired
  public ChatbotTemplateService(
      @Qualifier("chatbotTemplateRepository") ChatbotTemplateRepository chatbotTemplateRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.chatbotTemplateRepository = chatbotTemplateRepository;
    this.therapistRepository = therapistRepository;
  }

  public ChatbotTemplate createTemplate(String therapistId, ChatbotTemplate template) {
    Therapist therapist =
        therapistRepository
            .findById(therapistId)
            .orElseThrow(() -> new Error("Therapist not found with id: " + therapistId));
    template.setTherapist(therapist);
    template.setWorkspaceId(therapist.getWorkspaceId());
    return chatbotTemplateRepository.save(template);
  }

  public List<ChatbotTemplate> getTemplatesByTherapist(String therapistId) {
    return chatbotTemplateRepository.findByTherapistId(therapistId);
  }

  public ChatbotTemplate updateTemplate(
      String therapistId, String templateId, ChatbotTemplate template) {
    ChatbotTemplate existingTemplate =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));

    existingTemplate.setChatbotName(template.getChatbotName());

    return chatbotTemplateRepository.save(existingTemplate);
  }

  public void deleteTemplate(String therapistId, String templateId) {
    ChatbotTemplate template =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));
    chatbotTemplateRepository.delete(template);
  }

  public ChatbotTemplate cloneTemplate(String therapistId, String templateId) {
    ChatbotTemplate original =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));

    ChatbotTemplate clone = new ChatbotTemplate();
    clone.setChatbotName(original.getChatbotName() + " Clone");
    clone.setDescription(original.getDescription());
    clone.setChatbotModel(original.getChatbotModel());
    clone.setChatbotIcon(original.getChatbotIcon());
    clone.setChatbotLanguage(original.getChatbotLanguage());
    clone.setChatbotRole(original.getChatbotRole());
    clone.setChatbotTone(original.getChatbotTone());
    clone.setWelcomeMessage(original.getWelcomeMessage());
    clone.setTherapist(original.getTherapist());
    clone.setWorkspaceId(original.getWorkspaceId());

    return chatbotTemplateRepository.save(clone);
  }
}
