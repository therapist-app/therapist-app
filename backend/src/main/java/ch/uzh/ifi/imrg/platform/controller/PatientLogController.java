package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.LogOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.PatientLogService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class PatientLogController {

  private final PatientLogService logService;

  public PatientLogController(PatientLogService logService) {
    this.logService = logService;
  }

  @GetMapping("/patient/{patientId}/{logType}")
  public ResponseEntity<List<LogOutputDTO>> listLogs(
      @PathVariable String patientId,
      @PathVariable String logType,
      @CurrentTherapistId String therapistId) {
    List<LogOutputDTO> logs = logService.listAll(patientId, logType, therapistId);
    return ResponseEntity.ok(logs);
  }
}
