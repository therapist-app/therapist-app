package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.service.PatientDocumentService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/{patientId}")
  @ResponseStatus(HttpStatus.CREATED)
  public void createPatientDocument(
      @PathVariable String patientId,
      @RequestParam("file") MultipartFile file,
      HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    patientDocumentService.uploadPatientDocument(patientId, file, loggedInTherapist);
  }
}
