package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientDocumentMapper;
import ch.uzh.ifi.imrg.platform.service.PatientDocumentService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
@RequestMapping("/patient-documents")
public class PatientDocumentController {

  private final Logger logger = LoggerFactory.getLogger(PatientDocumentController.class);

  private final PatientDocumentService patientDocumentService;
  private final TherapistService therapistService;

  public PatientDocumentController(
      PatientDocumentService patientDocumentService, TherapistService therapistService) {
    this.patientDocumentService = patientDocumentService;
    this.therapistService = therapistService;
  }

  @PostMapping(path = "/{patientId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createPatientDocument(
      @PathVariable String patientId,
      @RequestParam("file") MultipartFile file,
      HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    patientDocumentService.uploadPatientDocument(patientId, file, loggedInTherapist);
  }

  @GetMapping("/{patientId}")
  public List<PatientDocumentOutputDTO> getDocumentsOfPatient(@PathVariable String patientId,
      HttpServletRequest httpServletReques) {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletReques);
    List<PatientDocumentOutputDTO> patientDocuments = patientDocumentService.getDocumentsOfPatient(patientId,
        loggedInTherapist);

    return patientDocuments;
  }

  @GetMapping("/{patientDocumentId}/download")
  public ResponseEntity<Resource> downloadFile(
      @PathVariable String patientDocumentId, HttpServletRequest httpServletRequest)
      throws IOException {
    Therapist loggedInTherapist = therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    PatientDocument fileDocument = patientDocumentService.downloadPatientDocument(patientDocumentId, loggedInTherapist);

    ByteArrayResource resource = new ByteArrayResource(fileDocument.getFileData());

    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + fileDocument.getFileName() + "\"")
        .contentType(MediaType.parseMediaType(fileDocument.getFileType()))
        .contentLength(fileDocument.getFileData().length)
        .body(resource);
  }
}
