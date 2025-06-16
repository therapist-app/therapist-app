package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.service.TherapistDocumentService;
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
@RequestMapping("/therapist-documents")
public class TherapistDocumentController {

  private final Logger logger = LoggerFactory.getLogger(TherapistDocumentController.class);

  private final TherapistDocumentService therapistDocumentService;
  private final TherapistService therapistService;

  public TherapistDocumentController(
      TherapistDocumentService therapistDocumentService, TherapistService therapistService) {
    this.therapistDocumentService = therapistDocumentService;
    this.therapistService = therapistService;
  }

  @PostMapping(path = "/{therapistId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createTherapistDocument(
      @PathVariable String therapistId,
      @RequestParam("file") MultipartFile file,
      HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    therapistDocumentService.uploadTherapistDocument(therapistId, file, loggedInTherapist);
  }

  @GetMapping("/{therapistId}")
  public List<TherapistDocumentOutputDTO> getDocumentsOfTherapist(
      @PathVariable String therapistId, HttpServletRequest httpServletReques) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletReques);
    List<TherapistDocumentOutputDTO> therapistDocuments =
        therapistDocumentService.getDocumentsOfTherapist(therapistId, loggedInTherapist);

    return therapistDocuments;
  }

  @GetMapping("/{therapistDocumentId}/download")
  public ResponseEntity<Resource> downloadFile(
      @PathVariable String therapistDocumentId, HttpServletRequest httpServletRequest)
      throws IOException {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    TherapistDocument fileDocument =
        therapistDocumentService.downloadTherapistDocument(therapistDocumentId, loggedInTherapist);

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
  public void deleteFile(
      @PathVariable String therapistDocumentId, HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    therapistDocumentService.deleteFile(therapistDocumentId, loggedInTherapist);
  }
}
