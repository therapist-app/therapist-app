package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.TherapistDocumentService;

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
@RequestMapping("/therapist-documents")
public class TherapistDocumentController {

  private final TherapistDocumentService therapistDocumentService;

  public TherapistDocumentController(
      TherapistDocumentService therapistDocumentService) {
    this.therapistDocumentService = therapistDocumentService;

  }

  @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createTherapistDocument(
      @RequestParam("file") MultipartFile file, @CurrentTherapistId String therapistId) {
    therapistDocumentService.uploadTherapistDocument(file, therapistId);
  }

  @GetMapping("/")
  public List<TherapistDocumentOutputDTO> getDocumentsOfTherapist(
      @CurrentTherapistId String therapistId) {
    List<TherapistDocumentOutputDTO> therapistDocuments = therapistDocumentService.getDocumentsOfTherapist(therapistId);

    return therapistDocuments;
  }

  @GetMapping("/{therapistDocumentId}/download")
  public ResponseEntity<Resource> downloadTherapistDocument(
      @PathVariable String therapistDocumentId, @CurrentTherapistId String therapistId)
      throws IOException {
    TherapistDocument fileDocument = therapistDocumentService.downloadTherapistDocument(therapistDocumentId,
        therapistId);

    ByteArrayResource resource = new ByteArrayResource(fileDocument.getFileData());

    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + fileDocument.getFileName() + "\"")
        .contentType(MediaType.parseMediaType(fileDocument.getFileType()))
        .contentLength(fileDocument.getFileData().length)
        .body(resource);
  }

  @DeleteMapping("/{therapistDocumentId}")
  public void deleteTherapistDocument(
      @PathVariable String therapistDocumentId, @CurrentTherapistId String therapistId) {
    therapistDocumentService.deleteFile(therapistDocumentId, therapistId);
  }
}
