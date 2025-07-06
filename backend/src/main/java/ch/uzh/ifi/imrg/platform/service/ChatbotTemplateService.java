package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatbotTemplateService {

  private final Logger logger = LoggerFactory.getLogger(ChatbotTemplateService.class);

  private final ChatbotTemplateRepository chatbotTemplateRepository;
  private final TherapistRepository therapistRepository;

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public ChatbotTemplateService(
      @Qualifier("chatbotTemplateRepository") ChatbotTemplateRepository chatbotTemplateRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.chatbotTemplateRepository = chatbotTemplateRepository;
    this.therapistRepository = therapistRepository;
  }

  public Therapist createTemplate(String therapistId, ChatbotTemplate template) {
    Therapist therapist =
        therapistRepository
            .findById(therapistId)
            .orElseThrow(() -> new Error("Therapist not found with id: " + therapistId));
    template.setTherapist(therapist);
    chatbotTemplateRepository.save(template);
    chatbotTemplateRepository.flush();

    entityManager.refresh(therapist);
    return therapist;
  }

  public Therapist updateTemplate(String therapistId, String templateId, ChatbotTemplate template) {

    ChatbotTemplate existingTemplate =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));

    existingTemplate.setChatbotName(template.getChatbotName());
    existingTemplate.setDescription(template.getDescription());
    existingTemplate.setChatbotModel(template.getChatbotModel());
    existingTemplate.setChatbotIcon(template.getChatbotIcon());
    existingTemplate.setChatbotLanguage(template.getChatbotLanguage());
    existingTemplate.setChatbotRole(template.getChatbotRole());
    existingTemplate.setChatbotTone(template.getChatbotTone());
    existingTemplate.setWelcomeMessage(template.getWelcomeMessage());
    existingTemplate.setChatbotVoice(template.getChatbotVoice());
    existingTemplate.setChatbotGender(template.getChatbotGender());
    existingTemplate.setPreConfiguredExercise(template.getPreConfiguredExercise());
    existingTemplate.setAdditionalExercise(template.getAdditionalExercise());
    existingTemplate.setAnimation(template.getAnimation());
    existingTemplate.setChatbotInputPlaceholder(template.getChatbotInputPlaceholder());

    chatbotTemplateRepository.save(existingTemplate);
    chatbotTemplateRepository.flush();

    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    entityManager.refresh(therapist);
    return therapist;
  }

  public Therapist deleteTemplate(String therapistId, String templateId) {

    ChatbotTemplate template =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));
    template.getTherapist().getChatbotTemplates().remove(template);

    chatbotTemplateRepository.delete(template);
    chatbotTemplateRepository.flush();

    Therapist therapist = therapistRepository.getReferenceById(therapistId);
    entityManager.refresh(therapist);
    return therapist;
  }

  public Therapist cloneTemplate(String therapistId, String templateId) {
    Therapist therapist = therapistRepository.getReferenceById(therapistId);
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
    clone.setChatbotVoice(original.getChatbotVoice());
    clone.setChatbotGender(original.getChatbotGender());
    clone.setPreConfiguredExercise(original.getPreConfiguredExercise());
    clone.setAdditionalExercise(original.getAdditionalExercise());
    clone.setAnimation(original.getAnimation());
    clone.setChatbotInputPlaceholder(original.getChatbotInputPlaceholder());
    clone.setTherapist(original.getTherapist());

    chatbotTemplateRepository.save(clone);
    chatbotTemplateRepository.flush();

    entityManager.refresh(therapist);
    return therapist;
  }

  public ChatbotTemplate findTemplateByIdAndTherapistId(String therapistId, String templateId) {
    return chatbotTemplateRepository
        .findByIdAndTherapistId(templateId, therapistId)
        .orElseThrow(() -> new Error("Template not found with id: " + templateId));
  }
}
