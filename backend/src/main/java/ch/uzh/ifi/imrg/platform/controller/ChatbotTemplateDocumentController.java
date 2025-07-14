package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.ChatbotTemplateDocumentService;
import java.io.IOException;
import java.util.List;
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

  private final ChatbotTemplateDocumentService chatbotTemplateDocumentService;

  public ChatbotTemplateDocumentController(
      ChatbotTemplateDocumentService chatbotTemplateDocumentService) {
    this.chatbotTemplateDocumentService = chatbotTemplateDocumentService;
  }

  @PostMapping(path = "/{templateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createChatbotTemplateDocument(
      @PathVariable String templateId,
      @RequestParam("file") MultipartFile file,
      @CurrentTherapistId String therapistId) {

    chatbotTemplateDocumentService.uploadChatbotTemplateDocument(templateId, file, therapistId);
  }

  @GetMapping("/{templateId}")
  public List<ChatbotTemplateDocumentOutputDTO> getDocumentsOfTemplate(
      @PathVariable String templateId, @CurrentTherapistId String therapistId) {

    return chatbotTemplateDocumentService.getDocumentsOfTemplate(templateId, therapistId);
  }

  @GetMapping("/{templateDocumentId}/download")
  public ResponseEntity<Resource> downloadChatbotTemplateDocument(
      @PathVariable String templateDocumentId, @CurrentTherapistId String therapistId)
      throws IOException {

    ChatbotTemplateDocument fileDocument =
        chatbotTemplateDocumentService.downloadChatbotTemplateDocument(
            templateDocumentId, therapistId);

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
      @PathVariable String templateDocumentId, @CurrentTherapistId String therapistId) {

    chatbotTemplateDocumentService.deleteFile(templateDocumentId, therapistId);
  }
}
