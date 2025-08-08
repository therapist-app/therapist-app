package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.LogOutputDTO;
import ch.uzh.ifi.imrg.platform.service.PatientLogService;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientLogControllerTest {

    @Test
    void listAndExportCsv() throws Exception {
        PatientLogService svc = mock(PatientLogService.class);
        PatientLogController ctrl = new PatientLogController(svc);

        LogOutputDTO dto = new LogOutputDTO();
        dto.setId("1");
        dto.setPatientId("pid");
        dto.setLogType("TYPE");
        dto.setTimestamp(Instant.parse("2024-01-01T00:00:00Z"));
        dto.setUniqueIdentifier("uid");

        when(svc.listAll("pid", "TYPE", "tid")).thenReturn(List.of(dto));
        ResponseEntity<List<LogOutputDTO>> resp = ctrl.listLogs("pid", "TYPE", "tid");
        assertEquals(200, resp.getStatusCode().value());
        assertEquals(1, resp.getBody().size());

        when(svc.listAllForPatient("pid", "tid")).thenReturn(List.of(dto));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        ctrl.exportAllLogsCsv("pid", "tid", response);

        pw.flush();
        String csv = sw.toString();
        assertTrue(csv.startsWith("id,patientId"));
        assertTrue(csv.contains("uid"));
        verify(response).setHeader(eq("Content-Disposition"), contains("logs-pid.csv"));
    }
}
