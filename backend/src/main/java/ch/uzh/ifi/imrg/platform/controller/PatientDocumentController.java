package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDocumentFromTherapistDocumentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.PatientDocumentService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
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
      @CurrentTherapistId String therapistId) {

    patientDocumentService.uploadPatientDocument(patientId, file, therapistId);
  }

  @PostMapping("/from-therapist-document")
  public void createPatientDocumentFromTherapistDocument(
      @RequestBody
          CreatePatientDocumentFromTherapistDocumentDTO
              createPatientDocumentFromTherapistDocumentDTO,
      @CurrentTherapistId String therapistId) {
    patientDocumentService.createPatientDocumentFromTherapistDocument(
        createPatientDocumentFromTherapistDocumentDTO, therapistId);
  }

  @GetMapping("/{patientId}")
  public List<PatientDocumentOutputDTO> getDocumentsOfPatient(
      @PathVariable String patientId, @CurrentTherapistId String therapistId) {
    List<PatientDocumentOutputDTO> patientDocuments =
        patientDocumentService.getDocumentsOfPatient(patientId, therapistId);

    return patientDocuments;
  }

  @GetMapping("/{patientDocumentId}/download")
  public ResponseEntity<Resource> downloadPatientDocument(
      @PathVariable String patientDocumentId, @CurrentTherapistId String therapistId)
      throws IOException {
    PatientDocument fileDocument =
        patientDocumentService.downloadPatientDocument(patientDocumentId, therapistId);

    ByteArrayResource resource = new ByteArrayResource(fileDocument.getFileData());

    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + fileDocument.getFileName() + "\"")
        .contentType(MediaType.parseMediaType(fileDocument.getFileType()))
        .contentLength(fileDocument.getFileData().length)
        .body(resource);
  }

  @DeleteMapping("/{patientDocumentId}")
  public void deletePatientDocument(
      @PathVariable String patientDocumentId, @CurrentTherapistId String therapistId) {

    patientDocumentService.deleteFile(patientDocumentId, therapistId);
  }
}
