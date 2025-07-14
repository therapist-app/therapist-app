package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.ChatbotTemplateDocumentMapper;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles persistence and access control for documents that belong to a Chatbot
 * Template. All
 * operations are verified against the {@link Therapist} who owns the template.
 */
@Service
@Transactional
public class ChatbotTemplateDocumentService {

  private static final Logger logger = LoggerFactory.getLogger(ChatbotTemplateDocumentService.class);

  private final ChatbotTemplateRepository chatbotTemplateRepository;
  private final ChatbotTemplateDocumentRepository chatbotTemplateDocumentRepository;

  public ChatbotTemplateDocumentService(
      @Qualifier("chatbotTemplateRepository") ChatbotTemplateRepository chatbotTemplateRepository,
      @Qualifier("chatbotTemplateDocumentRepository") ChatbotTemplateDocumentRepository chatbotTemplateDocumentRepository) {
    this.chatbotTemplateRepository = chatbotTemplateRepository;
    this.chatbotTemplateDocumentRepository = chatbotTemplateDocumentRepository;
  }

  public ChatbotTemplateDocument uploadChatbotTemplateDocument(
      String templateId, MultipartFile file, String therapistId) {

    ChatbotTemplate template = chatbotTemplateRepository
        .findByIdAndTherapistId(templateId, therapistId)
        .orElseThrow(() -> new EntityNotFoundException("Chatbot template not found"));

    String extractedText = DocumentParserUtil.extractText(file);

    ChatbotTemplateDocument templateDocument = new ChatbotTemplateDocument();
    templateDocument.setChatbotTemplate(template);
    templateDocument.setFileName(file.getOriginalFilename());
    templateDocument.setFileType(file.getContentType());
    try {
      templateDocument.setFileData(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file bytes", e);
    }
    templateDocument.setExtractedText(extractedText);

    ChatbotTemplateDocument saved = chatbotTemplateDocumentRepository.save(templateDocument);

    logger.info(
        "Uploaded document '{}' ({} bytes) for template '{}'",
        file.getOriginalFilename(),
        file.getSize(),
        templateId);

    return saved;
  }

  public List<ChatbotTemplateDocumentOutputDTO> getDocumentsOfTemplate(
      String templateId, String therapistId) {

    ChatbotTemplate template = chatbotTemplateRepository
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

    ChatbotTemplateDocument templateDocument = chatbotTemplateDocumentRepository
        .findById(templateDocumentId)
        .orElseThrow(() -> new EntityNotFoundException("Chatbot template document not found"));

    SecurityUtil.checkOwnership(templateDocument, therapistId);

    return templateDocument;
  }

  public void deleteFile(String templateDocumentId, String therapistId) {

    ChatbotTemplateDocument templateDocument = chatbotTemplateDocumentRepository
        .findById(templateDocumentId)
        .orElseThrow(() -> new EntityNotFoundException("Chatbot template document not found"));

    SecurityUtil.checkOwnership(templateDocument, therapistId);

    templateDocument.getChatbotTemplate().getChatbotTemplateDocuments().remove(templateDocument);
    logger.info(
        "Deleted document '{}' from template '{}'",
        templateDocumentId,
        templateDocument.getChatbotTemplate().getId());
  }

}
