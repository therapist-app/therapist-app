package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.LogOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.PatientLogService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.http.HttpHeaders;
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

  @GetMapping("/patient/{patientId}/export/csv")
  public void exportAllLogsCsv(
      @PathVariable String patientId,
      @CurrentTherapistId String therapistId,
      HttpServletResponse response)
      throws IOException {
    response.setContentType("text/csv");
    response.setHeader(
        HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"logs-" + patientId + ".csv\"");

    try (PrintWriter writer = response.getWriter()) {
      // Always write header
      writer.println("id,patientId,logType,timestamp,uniqueIdentifier");

      List<LogOutputDTO> allLogs = logService.listAllForPatient(patientId, therapistId);
      for (LogOutputDTO dto : allLogs) {
        writer.printf(
            "%s,%s,%s,%s,%s%n",
            dto.getId(),
            dto.getPatientId(),
            dto.getLogType(),
            dto.getTimestamp(),
            dto.getUniqueIdentifier());
      }
      writer.flush();
    }
  }
}
