package ch.uzh.ifi.imrg.platform.service;

import static ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs.coachChatbotControllerPatientAPI;

import ch.uzh.ifi.imrg.generated.model.ChatbotConfigurationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.UpdateChatbotDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ChatbotTemplateDocumentMapper;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.DocumentSummarizerUtil;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;

@Service
@Transactional
public class ChatbotTemplateDocumentService {

  private static final Logger logger =
      LoggerFactory.getLogger(ChatbotTemplateDocumentService.class);

  private final ChatbotTemplateRepository chatbotTemplateRepository;
  private final ChatbotTemplateDocumentRepository chatbotTemplateDocumentRepository;
  private final TherapistRepository therapistRepository;

  public ChatbotTemplateDocumentService(
      @Qualifier("chatbotTemplateRepository") ChatbotTemplateRepository chatbotTemplateRepository,
      @Qualifier("chatbotTemplateDocumentRepository")
          ChatbotTemplateDocumentRepository chatbotTemplateDocumentRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.chatbotTemplateRepository = chatbotTemplateRepository;
    this.chatbotTemplateDocumentRepository = chatbotTemplateDocumentRepository;
    this.therapistRepository = therapistRepository;
  }

  public ChatbotTemplateDocument uploadChatbotTemplateDocument(
      String templateId, MultipartFile file, String therapistId) {

    ChatbotTemplate template =
        chatbotTemplateRepository
            .findByIdAndTherapistId(templateId, therapistId)
            .orElseThrow(() -> new EntityNotFoundException("Chatbot template not found"));

    String rawText = readFullText(file);

    boolean needsSummary = rawText.length() > DocumentSummarizerUtil.MAX_SUMMARY_CHARS;
    String extractedText =
        needsSummary ? rawText.substring(0, DocumentSummarizerUtil.MAX_SUMMARY_CHARS) : rawText;

    ChatbotTemplateDocument doc = new ChatbotTemplateDocument();
    doc.setChatbotTemplate(template);
    doc.setFileName(file.getOriginalFilename());
    doc.setFileType(file.getContentType());
    try {
      doc.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file bytes", e);
    }
    doc.setExtractedText(extractedText);

    ChatbotTemplateDocument saved = chatbotTemplateDocumentRepository.save(doc);
    template.getChatbotTemplateDocuments().add(saved);

    if (!needsSummary) {
      updateChatbot(template);
    }

    if (needsSummary) {
      final String raw = rawText;
      final String docId = saved.getId();
      final String therapistPk = template.getTherapist().getId();

      CompletableFuture.runAsync(
          () -> {
            try {
              Therapist fresh = therapistRepository.findById(therapistPk).orElse(null);
              if (fresh == null) {
                logger.warn("Therapist {} vanished before summarisation", therapistPk);
                return;
              }

              String summary = DocumentSummarizerUtil.summarize(raw, fresh, therapistRepository);

              ChatbotTemplateDocument upd =
                  chatbotTemplateDocumentRepository.findById(docId).orElse(null);
              if (upd != null) {
                upd.setExtractedText(summary);
                chatbotTemplateDocumentRepository.save(upd);
                updateChatbot(upd.getChatbotTemplate());
              }
            } catch (Exception ex) {
              logger.error(
                  "Asynchronous LLM summarisation failed for ChatbotTemplateDocument {}",
                  docId,
                  ex);
            }
          });
    }

    return saved;
  }

  public List<ChatbotTemplateDocumentOutputDTO> getDocumentsOfTemplate(
      String templateId, String therapistId) {

    ChatbotTemplate template =
        chatbotTemplateRepository
            .findById(templateId)
            .orElseThrow(() -> new EntityNotFoundException("Chatbot template not found"));

    SecurityUtil.checkOwnership(template, therapistId);

    return template.getChatbotTemplateDocuments().stream()
        .map(
            ChatbotTemplateDocumentMapper.INSTANCE::convertEntityToChatbotTemplateDocumentOutputDTO)
        .collect(Collectors.toList());
  }

  public ChatbotTemplateDocument downloadChatbotTemplateDocument(
      String templateDocumentId, String therapistId) {

    ChatbotTemplateDocument doc =
        chatbotTemplateDocumentRepository
            .findById(templateDocumentId)
            .orElseThrow(() -> new EntityNotFoundException("Chatbot template document not found"));

    SecurityUtil.checkOwnership(doc, therapistId);
    return doc;
  }

  public void deleteFile(String templateDocumentId, String therapistId) {

    ChatbotTemplateDocument doc =
        chatbotTemplateDocumentRepository
            .findById(templateDocumentId)
            .orElseThrow(() -> new EntityNotFoundException("Chatbot template document not found"));

    SecurityUtil.checkOwnership(doc, therapistId);

    ChatbotTemplate template = doc.getChatbotTemplate();
    template.getChatbotTemplateDocuments().remove(doc);
    chatbotTemplateDocumentRepository.delete(doc);
    logger.info("Deleted document '{}' from template '{}'", templateDocumentId, template.getId());

    updateChatbot(template);
  }

  private void updateChatbot(ChatbotTemplate template) {
    if (template.getPatient() == null || !template.isActive()) {
      return;
    }

    String patientId = template.getPatient().getId();

    String chatbotContext = "\n\nNo documents for additional context";
    if (!template.getChatbotTemplateDocuments().isEmpty()) {
      StringBuilder sb =
          new StringBuilder(
              "\n\nHere are some documents which you can use for better context:\n\n");
      LLMContextBuilder.addLLMContextOfListOfEntities(
          sb, template.getChatbotTemplateDocuments(), "Chatbot Document", 0);
      chatbotContext = sb.toString();
    }

    ChatbotConfigurationOutputDTOPatientAPI firstConfig =
        coachChatbotControllerPatientAPI.getChatbotConfigurations(patientId).blockFirst();
    if (firstConfig == null) {
      return;
    }

    UpdateChatbotDTOPatientAPI dto =
        new UpdateChatbotDTOPatientAPI()
            .id(firstConfig.getId())
            .active(true)
            .chatbotRole(template.getChatbotRole())
            .chatbotTone(template.getChatbotTone())
            .welcomeMessage(template.getWelcomeMessage())
            .chatbotContext(chatbotContext);

    PatientAppAPIs.coachChatbotControllerPatientAPI.updateChatbot(patientId, dto).block();
  }

  private String readFullText(MultipartFile file) {
    try (InputStream stream = file.getInputStream()) {
      AutoDetectParser parser = new AutoDetectParser();
      ContentHandler handler = new BodyContentHandler(-1);
      Metadata metadata = new Metadata();
      parser.parse(stream, handler, metadata, new ParseContext());
      return handler.toString();
    } catch (Exception e) {
      return DocumentParserUtil.extractText(file);
    }
  }
}
