package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ChatbotTemplateMapper;
import jakarta.persistence.EntityNotFoundException;
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
  private final PatientRepository patientRepository;

  private final ChatbotTemplateMapper chatbotTemplateMapper = ChatbotTemplateMapper.INSTANCE;

  @Autowired
  public ChatbotTemplateService(
      @Qualifier("chatbotTemplateRepository") ChatbotTemplateRepository chatbotTemplateRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository,
      @Qualifier("patientRepository") PatientRepository patientRepository) {
    this.chatbotTemplateRepository = chatbotTemplateRepository;
    this.therapistRepository = therapistRepository;
    this.patientRepository = patientRepository;
  }

  public ChatbotTemplateOutputDTO getTemplateForTherapist(String therapistId, String templateId) {

    ChatbotTemplate tpl =
        chatbotTemplateRepository
            .findById(templateId)
            .orElseThrow(() -> new EntityNotFoundException("Template not found"));

    boolean allowed =
        (tpl.getTherapist() != null && tpl.getTherapist().getId().equals(therapistId))
            || (tpl.getPatient() != null
                && tpl.getPatient().getTherapist().getId().equals(therapistId));

    if (!allowed) {
      throw new EntityNotFoundException("Template not found");
    }
    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(tpl);
  }

  public ChatbotTemplateOutputDTO createTemplate(String therapistId, ChatbotTemplate template) {
    Therapist therapist =
        therapistRepository
            .findById(therapistId)
            .orElseThrow(() -> new Error("Therapist not found with id: " + therapistId));
    template.setTherapist(therapist);
    ChatbotTemplate createdChatbotTemplate = chatbotTemplateRepository.save(template);
    chatbotTemplateRepository.flush();

    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(createdChatbotTemplate);
  }

  public ChatbotTemplateOutputDTO createTemplateForPatient(
      String therapistId, String patientId, ChatbotTemplate template) {

    if (!patientRepository.existsByIdAndTherapistId(patientId, therapistId)) {
      throw new IllegalArgumentException(
          "Patient " + patientId + " does not belong to therapist " + therapistId);
    }

    Patient patient = patientRepository.getPatientById(patientId);

    template.setPatient(patient);
    template.setTherapist(patient.getTherapist());

    ChatbotTemplate saved = chatbotTemplateRepository.save(template);
    chatbotTemplateRepository.flush();

    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(saved);
  }

  public ChatbotTemplateOutputDTO updateTemplate(
      String therapistId, String templateId, ChatbotTemplate template) {

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

    ChatbotTemplate updChatbotTemplate = chatbotTemplateRepository.save(existingTemplate);
    chatbotTemplateRepository.flush();

    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(updChatbotTemplate);
  }

  public void deleteTemplate(String therapistId, String templateId) {

    ChatbotTemplate template =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));
    template.getTherapist().getChatbotTemplates().remove(template);

    chatbotTemplateRepository.delete(template);
    chatbotTemplateRepository.flush();
  }

  public void deleteTemplateForPatient(String therapistId, String patientId, String templateId) {

    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

    if (!patient.getTherapist().getId().equals(therapistId)) {
      throw new EntityNotFoundException("Patient not found for therapist");
    }

    ChatbotTemplate template =
        chatbotTemplateRepository
            .findByIdAndPatientId(templateId, patientId)
            .orElseThrow(() -> new EntityNotFoundException("Template not found"));

    patient.getChatbotTemplates().remove(template);
    template.getTherapist().getChatbotTemplates().remove(template);

    chatbotTemplateRepository.delete(template);
    chatbotTemplateRepository.flush();
  }

  public ChatbotTemplateOutputDTO cloneTemplate(String therapistId, String templateId) {
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

    ChatbotTemplate clonedTemplate = chatbotTemplateRepository.save(clone);
    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(clonedTemplate);
  }

  public ChatbotTemplateOutputDTO findTemplateByIdAndTherapistId(
      String therapistId, String templateId) {
    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId)));
  }

  @Transactional
  public ChatbotTemplateOutputDTO cloneTemplateForPatient(
      String therapistId, String patientId, String templateId) {

    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    if (!patient.getTherapist().getId().equals(therapistId)) {
      throw new EntityNotFoundException("Patient not found for therapist");
    }

    ChatbotTemplate original =
        chatbotTemplateRepository
            .findById(templateId)
            .orElseThrow(() -> new EntityNotFoundException("Template not found"));

    if (!original.getTherapist().getId().equals(therapistId)) {
      throw new EntityNotFoundException("Template not found for therapist");
    }

    ChatbotTemplate clone = new ChatbotTemplate();
    clone.setChatbotName(original.getChatbotName());
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

    clone.setTherapist(patient.getTherapist());
    clone.setPatient(patient);

    original
        .getChatbotTemplateDocuments()
        .forEach(
            src -> {
              ChatbotTemplateDocument dst = new ChatbotTemplateDocument();
              dst.setFileName(src.getFileName());
              dst.setFileType(src.getFileType());
              dst.setFileData(src.getFileData());
              dst.setExtractedText(src.getExtractedText());

              dst.setChatbotTemplate(clone);
              clone.getChatbotTemplateDocuments().add(dst);
            });

    ChatbotTemplate saved = chatbotTemplateRepository.save(clone);
    chatbotTemplateRepository.flush();

    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(saved);
  }
}
