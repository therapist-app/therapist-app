package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatCompletionWithConfigRequestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatbotConfigDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatCompletionResponseDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChatMessageService {

  private final ChatbotTemplateRepository chatbotTemplateRepository;

  public ChatMessageService(ChatbotTemplateRepository chatbotTemplateRepository) {
    this.chatbotTemplateRepository = chatbotTemplateRepository;
  }

  /** Main entry point called by the controller. */
  public ChatCompletionResponseDTO chat(
      ChatCompletionWithConfigRequestDTO req, String therapistId) {

    ChatbotTemplate template =
        chatbotTemplateRepository
            .findByIdAndTherapistId(req.getTemplateId(), therapistId)
            .orElseThrow(() -> new EntityNotFoundException("Chatbot template not found"));

    String systemPrompt = buildSystemPrompt(req.getConfig(), template);

    List<ChatMessageDTO> msgs = new ArrayList<>();
    msgs.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    if (req.getHistory() != null && !req.getHistory().isEmpty()) msgs.addAll(req.getHistory());
    msgs.add(new ChatMessageDTO(ChatRole.USER, req.getMessage()));

    String responseMessage = LLMUZH.callLLM(msgs, req.getLanguage());
    return new ChatCompletionResponseDTO(responseMessage);
  }

  private String buildSystemPrompt(ChatbotConfigDTO c, ChatbotTemplate template) {

    StringBuilder sb = new StringBuilder("You are a helpful assistant.");

    if (nonEmpty(c.getChatbotRole()))
      sb.append("\nYour role is **").append(c.getChatbotRole()).append("**.");
    if (nonEmpty(c.getChatbotTone()))
      sb.append("\nSpeak with a **").append(c.getChatbotTone()).append("** tone.");
    if (nonEmpty(c.getWelcomeMessage()))
      sb.append("\nYour default welcome message is: “").append(c.getWelcomeMessage()).append("”.");

    DateTimeFormatter fmt =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());
    sb.append("\n---\n## General Context\n");
    sb.append("- **Current Date and Time:** ")
        .append(fmt.format(java.time.Instant.now()))
        .append("\n\n");

    if (template.getChatbotTemplateDocuments() != null
        && !template.getChatbotTemplateDocuments().isEmpty()) {

      sb.append("## Template Documents\n");
      sb.append(
          "The following documents belong to this chatbot template. Use them as authoritative sources when answering.\n\n");

      for (ChatbotTemplateDocument doc : template.getChatbotTemplateDocuments()) {
        if (nonEmpty(doc.getExtractedText())) {
          sb.append("### Document: ")
              .append(doc.getFileName() != null ? doc.getFileName() : "Untitled")
              .append("\n```text\n")
              .append(doc.getExtractedText().trim())
              .append("\n```\n\n");
        }
      }
    }

    return sb.toString();
  }

  private boolean nonEmpty(String s) {
    return s != null && !s.isBlank();
  }
}
