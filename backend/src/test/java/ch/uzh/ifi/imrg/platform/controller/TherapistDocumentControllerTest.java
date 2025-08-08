package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.service.TherapistDocumentService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TherapistDocumentControllerTest {

    TherapistDocumentService svc = mock(TherapistDocumentService.class);
    TherapistDocumentController ctrl = new TherapistDocumentController(svc);

    @Test
    void endpoints_delegateAndBuildResponse() throws Exception {
        MultipartFile f = mock(MultipartFile.class);
        ctrl.createTherapistDocument(f,"tid");
        verify(svc).uploadTherapistDocument(f,"tid");

        List<TherapistDocumentOutputDTO> list = List.of(new TherapistDocumentOutputDTO());
        when(svc.getDocumentsOfTherapist("tid")).thenReturn(list);
        assertSame(list, ctrl.getDocumentsOfTherapist("tid"));

        TherapistDocument d = new TherapistDocument();
        d.setFileName("n.txt");
        d.setFileType("text/plain");
        d.setFileData(new byte[]{1,2});
        when(svc.downloadTherapistDocument("did","tid")).thenReturn(d);
        ResponseEntity<Resource> resp = ctrl.downloadTherapistDocument("did","tid");
        assertEquals("attachment; filename=\"n.txt\"",
                resp.getHeaders().getFirst("Content-Disposition"));
        assertEquals("text/plain", resp.getHeaders().getContentType().toString());
        assertEquals(2, resp.getHeaders().getContentLength());
        assertArrayEquals(d.getFileData(),
                resp.getBody().getContentAsByteArray());

        ctrl.deleteTherapistDocument("did","tid");
        verify(svc).deleteFile("did","tid");
    }
}
