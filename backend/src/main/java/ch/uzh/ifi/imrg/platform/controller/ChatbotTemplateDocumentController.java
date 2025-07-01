package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.service.ChatbotTemplateDocumentService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chatbot-template-documents")
public class ChatbotTemplateDocumentController {

  private static final Logger logger =
      LoggerFactory.getLogger(ChatbotTemplateDocumentController.class);

  private final ChatbotTemplateDocumentService chatbotTemplateDocumentService;
  private final TherapistService therapistService;

  public ChatbotTemplateDocumentController(
      ChatbotTemplateDocumentService chatbotTemplateDocumentService,
      TherapistService therapistService) {
    this.chatbotTemplateDocumentService = chatbotTemplateDocumentService;
    this.therapistService = therapistService;
  }

  @PostMapping(path = "/{templateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createChatbotTemplateDocument(
      @PathVariable String templateId,
      @RequestParam("file") MultipartFile file,
      HttpServletRequest request) {

    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(request);
    chatbotTemplateDocumentService.uploadChatbotTemplateDocument(
        templateId, file, loggedInTherapist);
  }

  @GetMapping("/{templateId}")
  public List<ChatbotTemplateDocumentOutputDTO> getDocumentsOfTemplate(
      @PathVariable String templateId, HttpServletRequest request) {

    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(request);
    return chatbotTemplateDocumentService.getDocumentsOfTemplate(templateId, loggedInTherapist);
  }

  @GetMapping("/{templateDocumentId}/download")
  public ResponseEntity<Resource> downloadChatbotTemplateDocument(
      @PathVariable String templateDocumentId, HttpServletRequest request) throws IOException {

    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(request);
    ChatbotTemplateDocument fileDocument =
        chatbotTemplateDocumentService.downloadChatbotTemplateDocument(
            templateDocumentId, loggedInTherapist);

    ByteArrayResource resource = new ByteArrayResource(fileDocument.getFileData());

    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + fileDocument.getFileName() + "\"")
        .contentType(MediaType.parseMediaType(fileDocument.getFileType()))
        .contentLength(fileDocument.getFileData().length)
        .body(resource);
  }

  @DeleteMapping("/{templateDocumentId}")
  public void deleteChatbotTemplateDocument(
      @PathVariable String templateDocumentId, HttpServletRequest request) {

    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(request);
    chatbotTemplateDocumentService.deleteFile(templateDocumentId, loggedInTherapist);
  }
}
