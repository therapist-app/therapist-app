package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.CreateChatbotDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ChatbotTemplateMapper;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatbotTemplateService {

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

  public ChatbotTemplateOutputDTO getTemplateForTherapist(String templateId, String therapistId) {

    ChatbotTemplate tpl =
        chatbotTemplateRepository
            .findById(templateId)
            .orElseThrow(() -> new EntityNotFoundException("Template not found"));

    SecurityUtil.checkOwnership(tpl, therapistId);

    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(tpl);
  }

  public ChatbotTemplateOutputDTO createTemplate(ChatbotTemplate template, String therapistId) {
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
      String patientId, ChatbotTemplate template, String therapistId) {

    if (!patientRepository.existsByIdAndTherapistId(patientId, therapistId)) {
      throw new IllegalArgumentException(
          "Patient " + patientId + " does not belong to therapist " + therapistId);
    }

    Patient patient = patientRepository.getPatientById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    template.setPatient(patient);
    template.setTherapist(patient.getTherapist());

    ChatbotTemplate saved = chatbotTemplateRepository.save(template);
    chatbotTemplateRepository.flush();

    CreateChatbotDTOPatientAPI createChatbotDTO =
        new CreateChatbotDTOPatientAPI()
            .chatbotRole(saved.getChatbotRole())
            .chatbotTone(saved.getChatbotTone())
            .welcomeMessage(saved.getWelcomeMessage());

    PatientAppAPIs.coachChatbotControllerPatientAPI
        .createChatbot(patientId, createChatbotDTO)
        .block();
    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(saved);
  }

  public ChatbotTemplateOutputDTO updateTemplate(
      String templateId, ChatbotTemplate template, String therapistId) {

    ChatbotTemplate existingTemplate =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));
    SecurityUtil.checkOwnership(existingTemplate, therapistId);

    existingTemplate.setChatbotName(template.getChatbotName());
    existingTemplate.setChatbotIcon(template.getChatbotIcon());
    existingTemplate.setChatbotRole(template.getChatbotRole());
    existingTemplate.setChatbotTone(template.getChatbotTone());
    existingTemplate.setWelcomeMessage(template.getWelcomeMessage());

    ChatbotTemplate updChatbotTemplate = chatbotTemplateRepository.save(existingTemplate);
    chatbotTemplateRepository.flush();

    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(updChatbotTemplate);
  }

  public void deleteTemplate(String templateId, String therapistId) {

    ChatbotTemplate template =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));
    SecurityUtil.checkOwnership(template, therapistId);

    template.getTherapist().getChatbotTemplates().remove(template);

    chatbotTemplateRepository.delete(template);
    chatbotTemplateRepository.flush();
  }

  public void deleteTemplateForPatient(String patientId, String templateId, String therapistId) {

    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

    SecurityUtil.checkOwnership(patient, therapistId);

    ChatbotTemplate template =
        chatbotTemplateRepository
            .findByIdAndPatientId(templateId, patientId)
            .orElseThrow(() -> new EntityNotFoundException("Template not found"));

    patient.getChatbotTemplates().remove(template);
    template.getTherapist().getChatbotTemplates().remove(template);

    chatbotTemplateRepository.delete(template);
    chatbotTemplateRepository.flush();
  }

  public ChatbotTemplateOutputDTO cloneTemplate(String templateId, String therapistId) {
    ChatbotTemplate original =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));

    SecurityUtil.checkOwnership(original, therapistId);

    ChatbotTemplate clone = new ChatbotTemplate();
    clone.setChatbotName(original.getChatbotName() + " Clone");
    clone.setChatbotIcon(original.getChatbotIcon());
    clone.setChatbotRole(original.getChatbotRole());
    clone.setChatbotTone(original.getChatbotTone());
    clone.setWelcomeMessage(original.getWelcomeMessage());
    clone.setTherapist(original.getTherapist());

    ChatbotTemplate clonedTemplate = chatbotTemplateRepository.save(clone);
    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(clonedTemplate);
  }

  public ChatbotTemplateOutputDTO findTemplateByIdAndTherapistId(
      String templateId, String therapistId) {
    ChatbotTemplate chatbotTemplate =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new Error("Template not found with id: " + templateId));
    SecurityUtil.checkOwnership(chatbotTemplate, therapistId);

    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(chatbotTemplate);
  }

  @Transactional
  public ChatbotTemplateOutputDTO cloneTemplateForPatient(
      String patientId, String templateId, String therapistId) {

    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    SecurityUtil.checkOwnership(patient, therapistId);

    ChatbotTemplate original =
        chatbotTemplateRepository
            .findById(templateId)
            .orElseThrow(() -> new EntityNotFoundException("Template not found"));

    SecurityUtil.checkOwnership(original, therapistId);

    ChatbotTemplate clone = new ChatbotTemplate();
    clone.setChatbotName(original.getChatbotName() + " Clone");
    clone.setChatbotIcon(original.getChatbotIcon());
    clone.setChatbotRole(original.getChatbotRole());
    clone.setChatbotTone(original.getChatbotTone());
    clone.setWelcomeMessage(original.getWelcomeMessage());

    clone.setTherapist(patient.getTherapist());
    clone.setPatient(patient);

    original
        .getChatbotTemplateDocuments()
        .forEach(
            doc -> {
              ChatbotTemplateDocument copy = new ChatbotTemplateDocument();
              copy.setFileName(doc.getFileName());
              copy.setFileType(doc.getFileType());
              copy.setFileData(doc.getFileData());
              copy.setExtractedText(doc.getExtractedText());
              copy.setChatbotTemplate(clone);
              clone.getChatbotTemplateDocuments().add(copy);
            });

    ChatbotTemplate saved = chatbotTemplateRepository.save(clone);
    chatbotTemplateRepository.flush();

    return chatbotTemplateMapper.convertEntityToChatbotTemplateOutputDTO(saved);
  }
}
