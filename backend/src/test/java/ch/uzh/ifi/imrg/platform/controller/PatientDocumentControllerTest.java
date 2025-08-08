package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDocumentFromTherapistDocumentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.service.PatientDocumentService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientDocumentControllerTest {

    @Test
    void everyEndpointDelegates() throws Exception {
        PatientDocumentService svc = mock(PatientDocumentService.class);
        PatientDocumentController ctrl = new PatientDocumentController(svc);

        MultipartFile mf = Mockito.mock(MultipartFile.class);

        ctrl.createPatientDocument("pid", mf, Boolean.TRUE, "tid");
        verify(svc).uploadPatientDocument("pid", mf, true, "tid");

        CreatePatientDocumentFromTherapistDocumentDTO dto =
                new CreatePatientDocumentFromTherapistDocumentDTO();
        ctrl.createPatientDocumentFromTherapistDocument(dto, "tid");
        verify(svc).createPatientDocumentFromTherapistDocument(dto, "tid");

        when(svc.getDocumentsOfPatient("pid", "tid"))
                .thenReturn(List.of(new PatientDocumentOutputDTO()));
        assertEquals(
                1, ctrl.getDocumentsOfPatient("pid", "tid").size());
        verify(svc).getDocumentsOfPatient("pid", "tid");

        PatientDocument doc = new PatientDocument();
        doc.setFileName("f"); doc.setFileType("text/plain"); doc.setFileData("x".getBytes());
        when(svc.downloadPatientDocument("did", "tid")).thenReturn(doc);
        ResponseEntity<Resource> re = ctrl.downloadPatientDocument("did", "tid");
        assertEquals(200, re.getStatusCode().value());
        assertEquals("attachment; filename=\"f\"",
                re.getHeaders().getFirst("Content-Disposition"));

        ctrl.deletePatientDocument("did", "tid");
        verify(svc).deleteFile("did", "tid");
    }
}
