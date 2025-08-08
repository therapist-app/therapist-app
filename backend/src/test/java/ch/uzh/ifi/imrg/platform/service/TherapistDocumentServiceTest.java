package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.repository.TherapistDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.DocumentSummarizerUtil;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
class TherapistDocumentServiceTest {

    TherapistRepository repo = mock(TherapistRepository.class);
    TherapistDocumentRepository dRepo = mock(TherapistDocumentRepository.class);
    TherapistDocumentService svc = new TherapistDocumentService(repo, dRepo);

    MultipartFile file(String name, String ct, byte[] bytes) throws Exception {
        MultipartFile f = mock(MultipartFile.class);
        when(f.getOriginalFilename()).thenReturn(name);
        when(f.getContentType()).thenReturn(ct);
        when(f.getBytes()).thenReturn(bytes);
        when(f.getInputStream()).thenThrow(new RuntimeException());
        return f;
    }

    @Test
    void upload_ok_and_summary_branch() throws Exception {
        Therapist t = new Therapist();
        when(repo.findById("tid")).thenReturn(Optional.of(t));
        try (MockedStatic<DocumentParserUtil> dp = Mockito.mockStatic(DocumentParserUtil.class)) {
            String shortTxt = "a";
            dp.when(() -> DocumentParserUtil.extractText(any())).thenReturn(shortTxt);
            TherapistDocument doc = new TherapistDocument();
            when(dRepo.save(any())).thenAnswer(inv -> {
                TherapistDocument d = inv.getArgument(0);
                d.setId("id");
                return d;
            });
            svc.uploadTherapistDocument(file("f.txt","text/plain","x".getBytes()),"tid");
            verify(dRepo).save(any());
            String longTxt = "x".repeat(DocumentSummarizerUtil.MAX_SUMMARY_CHARS + 1);
            dp.when(() -> DocumentParserUtil.extractText(any())).thenReturn(longTxt);
            svc.uploadTherapistDocument(file("b","t","z".getBytes()),"tid");
        }
    }

    @Test
    void upload_therapistMissing_and_badBytes() throws Exception {
        when(repo.findById("x")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> svc.uploadTherapistDocument(file("a","t","y".getBytes()),"x"));
        Therapist t = new Therapist();
        when(repo.findById("tid")).thenReturn(Optional.of(t));
        MultipartFile f = mock(MultipartFile.class);
        when(f.getOriginalFilename()).thenReturn("n");
        when(f.getContentType()).thenReturn("c");
        when(f.getBytes()).thenThrow(new java.io.IOException());
        when(f.getInputStream()).thenThrow(new RuntimeException());
        try (MockedStatic<DocumentParserUtil> dp = Mockito.mockStatic(DocumentParserUtil.class)) {
            dp.when(() -> DocumentParserUtil.extractText(any())).thenReturn("q");
            assertThrows(ResponseStatusException.class,
                    () -> svc.uploadTherapistDocument(f,"tid"));
        }
    }

    @Test
    void list_download_delete_allBranches() {
        Therapist t = new Therapist();
        TherapistDocument d = new TherapistDocument();
        d.setId("did");
        d.setFileName("n");
        d.setFileType("t");
        d.setFileData(new byte[]{1});
        d.setTherapist(t);
        t.getTherapistDocuments().add(d);
        when(repo.findById("tid")).thenReturn(Optional.of(t));
        assertEquals(1, svc.getDocumentsOfTherapist("tid").size());
        when(repo.findById("no")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> svc.getDocumentsOfTherapist("no"));
        when(dRepo.findById("did")).thenReturn(Optional.of(d));
        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            assertSame(d, svc.downloadTherapistDocument("did", "tid"));
            sec.verify(() -> SecurityUtil.checkOwnership(d, "tid"));
            sec.when(() -> SecurityUtil.checkOwnership(d,"bad"))
                    .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN));
            assertThrows(ResponseStatusException.class,
                    () -> svc.downloadTherapistDocument("did","bad"));
        }
        when(dRepo.findById("miss")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> svc.downloadTherapistDocument("miss","tid"));
        doReturn(d).when(dRepo).getReferenceById("did");
        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            svc.deleteFile("did","tid");
            assertTrue(t.getTherapistDocuments().isEmpty());
            sec.verify(() -> SecurityUtil.checkOwnership(d,"tid"));
            sec.when(() -> SecurityUtil.checkOwnership(d,"bad")).thenThrow(new RuntimeException());
            assertThrows(RuntimeException.class, () -> svc.deleteFile("did","bad"));
        }
    }
}
