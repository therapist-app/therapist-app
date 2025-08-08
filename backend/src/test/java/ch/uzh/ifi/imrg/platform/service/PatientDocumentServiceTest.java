package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.DocumentOverviewDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.repository.PatientDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDocumentFromTherapistDocumentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.DocumentSummarizerUtil;
import ch.uzh.ifi.imrg.platform.utils.FileUploadUtil;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PatientDocumentServiceTest {

    @Test
    void uploadPatientDocument_notShared_shortText_success() throws Exception {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        Patient patient = mock(Patient.class);
        when(pRepo.findById("P1")).thenReturn(Optional.of(patient));

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("f.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("hello".getBytes(StandardCharsets.UTF_8)));
        when(file.getBytes()).thenReturn("bytes".getBytes(StandardCharsets.UTF_8));

        PatientDocument saved = new PatientDocument();
        saved.setId("DOC1");
        when(pdRepo.save(any(PatientDocument.class))).thenReturn(saved);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.uploadPatientDocument("P1", file, false, "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(patient, "TH"));
        }

        verify(pdRepo, atLeastOnce()).save(any(PatientDocument.class));
        verifyNoInteractions(tRepo);
    }

    @Test
    void uploadPatientDocument_shared_longText_async_therapistNull() throws Exception {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        Patient patient = mock(Patient.class);
        when(pRepo.findById("P2")).thenReturn(Optional.of(patient));

        String longText = "X".repeat(20000);
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("big.pdf");
        when(file.getContentType()).thenReturn("application/pdf");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(longText.getBytes(StandardCharsets.UTF_8)));
        when(file.getBytes()).thenReturn(longText.getBytes(StandardCharsets.UTF_8));

        PatientDocument saved = new PatientDocument();
        saved.setId("DOC2");
        when(pdRepo.save(any(PatientDocument.class))).thenReturn(saved);

        DocumentOverviewDTOPatientAPI dto = new DocumentOverviewDTOPatientAPI();
        dto.setId("EXT2");

        try (MockedStatic<FileUploadUtil> up = mockStatic(FileUploadUtil.class);
             MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            up.when(() -> FileUploadUtil.uploadFile(anyString(), eq(file))).thenReturn(dto);
            when(tRepo.findById("TH2")).thenReturn(Optional.empty());

            svc.uploadPatientDocument("P2", file, true, "TH2");

            up.verify(() -> FileUploadUtil.uploadFile(contains("/coach/patients/P2/documents"), eq(file)));
            sec.verify(() -> SecurityUtil.checkOwnership(patient, "TH2"));
        }

        verify(pdRepo, atLeastOnce()).save(any(PatientDocument.class));
    }

    @Test
    void uploadPatientDocument_shared_longText_async_summaryThrows_logsError() throws Exception {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        Patient patient = mock(Patient.class);
        when(pRepo.findById("P4")).thenReturn(Optional.of(patient));

        String longText = "Z".repeat(22000);
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("big3.pdf");
        when(file.getContentType()).thenReturn("application/pdf");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(longText.getBytes(StandardCharsets.UTF_8)));
        when(file.getBytes()).thenReturn(longText.getBytes(StandardCharsets.UTF_8));

        PatientDocument firstSaved = new PatientDocument();
        firstSaved.setId("DOC4");
        when(pdRepo.save(any(PatientDocument.class))).thenReturn(firstSaved);

        when(pdRepo.findById("DOC4")).thenReturn(Optional.of(new PatientDocument()));

        Therapist therapist = mock(Therapist.class);
        when(tRepo.findById("TH4")).thenReturn(Optional.of(therapist));

        DocumentOverviewDTOPatientAPI dto = new DocumentOverviewDTOPatientAPI();
        dto.setId("EXT4");

        try (MockedStatic<FileUploadUtil> up = mockStatic(FileUploadUtil.class);
             MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class);
             MockedStatic<DocumentSummarizerUtil> sum = mockStatic(DocumentSummarizerUtil.class)) {
            up.when(() -> FileUploadUtil.uploadFile(anyString(), eq(file))).thenReturn(dto);
            sum.when(() -> DocumentSummarizerUtil.summarize(anyString(), any(Therapist.class), any(TherapistRepository.class)))
                    .thenThrow(new RuntimeException("boom"));

            svc.uploadPatientDocument("P4", file, true, "TH4");
            verify(pdRepo, timeout(1000)).save(any(PatientDocument.class));
            sec.verify(() -> SecurityUtil.checkOwnership(patient, "TH4"));
        }
    }

    @Test
    void uploadPatientDocument_fileBytesFail_badRequest() throws Exception {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        Patient patient = mock(Patient.class);
        when(pRepo.findById("P5")).thenReturn(Optional.of(patient));

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("f.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("ok".getBytes(StandardCharsets.UTF_8)));
        when(file.getBytes()).thenThrow(new IOException("x"));

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> svc.uploadPatientDocument("P5", file, false, "TH5"));
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }

    @Test
    void createPatientDocumentFromTherapistDocument_happyPath() {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        TherapistDocument td = mock(TherapistDocument.class);
        when(td.getFileName()).thenReturn("n");
        when(td.getFileType()).thenReturn("t");
        when(td.getFileData()).thenReturn(new byte[]{1});
        when(td.getExtractedText()).thenReturn("txt");
        when(tdRepo.findById("TD")).thenReturn(Optional.of(td));

        Patient pat = mock(Patient.class);
        when(pRepo.findById("P")).thenReturn(Optional.of(pat));

        CreatePatientDocumentFromTherapistDocumentDTO dto = new CreatePatientDocumentFromTherapistDocumentDTO();
        dto.setTherapistDocumentId("TD");
        dto.setPatientId("P");

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.createPatientDocumentFromTherapistDocument(dto, "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(td, "TH"));
        }

        verify(pdRepo).save(any(PatientDocument.class));
    }

    @Test
    void createPatientDocumentFromTherapistDocument_missingTd_notFound() {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        when(tdRepo.findById("NA")).thenReturn(Optional.empty());
        CreatePatientDocumentFromTherapistDocumentDTO dto = new CreatePatientDocumentFromTherapistDocumentDTO();
        dto.setTherapistDocumentId("NA");
        dto.setPatientId("P");
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> svc.createPatientDocumentFromTherapistDocument(dto, "TH"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void getDocumentsOfPatient_ok_emptyList() {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        Patient patient = mock(Patient.class);
        when(patient.getPatientDocuments()).thenReturn(new ArrayList<>());
        when(pRepo.getPatientById("P")).thenReturn(patient);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            List<PatientDocumentOutputDTO> out = svc.getDocumentsOfPatient("P", "TH");
            assertNotNull(out);
            assertEquals(0, out.size());
            sec.verify(() -> SecurityUtil.checkOwnership(patient, "TH"));
        }
    }

    @Test
    void downloadPatientDocument_success_and_notFound() {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        PatientDocument doc = mock(PatientDocument.class);
        when(pdRepo.findById("D1")).thenReturn(Optional.of(doc));

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            assertSame(doc, svc.downloadPatientDocument("D1", "TH"));
            sec.verify(() -> SecurityUtil.checkOwnership(doc, "TH"));
        }

        when(pdRepo.findById("NX")).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> svc.downloadPatientDocument("NX", "TH"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteFile_shared_external_okAndNotFound() {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        Patient patient = mock(Patient.class);
        PatientDocument doc = mock(PatientDocument.class);
        when(doc.getIsSharedWithPatient()).thenReturn(true);
        when(doc.getExternalId()).thenReturn("EXT");
        when(doc.getPatient()).thenReturn(patient);
        when(patient.getId()).thenReturn("P");
        List<PatientDocument> docs = new ArrayList<>();
        docs.add(doc);
        when(patient.getPatientDocuments()).thenReturn(docs);

        PatientAppAPIs.coachDocumentControllerPatientAPI =
                mock(ch.uzh.ifi.imrg.generated.api.CoachDocumentControllerPatientAPI.class);
        when(PatientAppAPIs.coachDocumentControllerPatientAPI.deleteDocument(anyString(), anyString()))
                .thenReturn(Mono.empty());

        when(pdRepo.getReferenceById("ID")).thenReturn(doc);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.deleteFile("ID", "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(doc, "TH"));

            assertFalse(docs.contains(doc));

            docs.add(doc);
            when(PatientAppAPIs.coachDocumentControllerPatientAPI.deleteDocument(anyString(), anyString()))
                    .thenReturn(Mono.error(WebClientResponseException.create(
                            404, "nf", new HttpHeaders(), new byte[0], StandardCharsets.UTF_8
                    )));
            when(pdRepo.getReferenceById("ID2")).thenReturn(doc);
            svc.deleteFile("ID2", "TH2");
            assertFalse(docs.contains(doc));
        }
    }

    @Test
    void deleteFile_notSharedOrNoExternal() {
        PatientRepository pRepo = mock(PatientRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        TherapistDocumentRepository tdRepo = mock(TherapistDocumentRepository.class);
        PatientDocumentRepository pdRepo = mock(PatientDocumentRepository.class);
        PatientDocumentService svc = new PatientDocumentService(pRepo, tRepo, tdRepo, pdRepo);

        Patient patient = mock(Patient.class);
        PatientDocument doc = mock(PatientDocument.class);
        when(doc.getIsSharedWithPatient()).thenReturn(false);
        when(doc.getExternalId()).thenReturn(null);
        when(doc.getPatient()).thenReturn(patient);
        List<PatientDocument> docs = new ArrayList<>();
        docs.add(doc);
        when(patient.getPatientDocuments()).thenReturn(docs);

        when(pdRepo.getReferenceById("ID3")).thenReturn(doc);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.deleteFile("ID3", "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(doc, "TH"));
        }
        assertFalse(docs.contains(doc));
    }
}
