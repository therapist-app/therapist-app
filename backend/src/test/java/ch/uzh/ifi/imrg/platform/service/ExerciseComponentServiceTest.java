package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.ExerciseComponentUpdateInputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ExerciseComponentRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.utils.DocumentParserUtil;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ExerciseComponentServiceTest {

    @SuppressWarnings({"unchecked","rawtypes"})
    private static Object firstEnumConstantFromGetter(Class<?> owner, String getter) throws Exception {
        Method m = owner.getMethod(getter);
        Class<?> enumType = m.getReturnType();
        Object[] all = ((Class<? extends Enum>) enumType).getEnumConstants();
        return all != null && all.length > 0 ? all[0] : null;
    }

    @Test
    void createExerciseComponent_callsApi() throws Exception {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        Exercise exercise = mock(Exercise.class);
        List<ExerciseComponent> list = new ArrayList<>();
        when(exercise.getExerciseComponents()).thenReturn(list);
        Patient pat = mock(Patient.class);
        when(pat.getId()).thenReturn("P");
        when(exercise.getPatient()).thenReturn(pat);
        when(eRepo.getReferenceById("EX")).thenReturn(exercise);

        ExerciseComponent saved = mock(ExerciseComponent.class);
        when(saved.getId()).thenReturn("ECID");
        Object ecTypeConst = firstEnumConstantFromGetter(ExerciseComponent.class, "getExerciseComponentType");
        when(saved.getExerciseComponentType()).thenAnswer(inv -> ecTypeConst);
        when(saved.getExerciseComponentDescription()).thenReturn("desc");
        when(saved.getOrderNumber()).thenReturn(1);
        when(saved.getYoutubeUrl()).thenReturn("y");
        when(ecRepo.save(any(ExerciseComponent.class))).thenReturn(saved);

        PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.createExerciseComponent(anyString(), anyString(), any()))
                .thenReturn(Mono.empty());

        CreateExerciseComponentDTO dto = mock(CreateExerciseComponentDTO.class);
        when(dto.getExerciseId()).thenReturn("EX");
        Object dtoEnumConst = firstEnumConstantFromGetter(CreateExerciseComponentDTO.class, "getExerciseComponentType");
        when(dto.getExerciseComponentType()).thenAnswer(inv -> dtoEnumConst);
        when(dto.getExerciseComponentDescription()).thenReturn("desc");
        when(dto.getYoutubeUrl()).thenReturn("y");

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.createExerciseComponent(dto, "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(exercise, "TH"));
        }

        verify(PatientAppAPIs.coachExerciseControllerPatientAPI).createExerciseComponent(eq("P"), eq("EX"), any());
        verify(ecRepo).save(any(ExerciseComponent.class));
    }

    @Test
    void createExerciseComponentWithFile_shortText_noSummary() throws Exception {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        Exercise exercise = mock(Exercise.class);
        List<ExerciseComponent> list = new ArrayList<>();
        when(exercise.getExerciseComponents()).thenReturn(list);
        Patient pat = mock(Patient.class);
        when(pat.getId()).thenReturn("P");
        Therapist therapist = mock(Therapist.class);
        when(pat.getTherapist()).thenReturn(therapist);
        when(exercise.getPatient()).thenReturn(pat);
        when(eRepo.getReferenceById("EX")).thenReturn(exercise);

        ExerciseComponent saved = mock(ExerciseComponent.class);
        when(saved.getId()).thenReturn("EC1");
        Object ecTypeConst = firstEnumConstantFromGetter(ExerciseComponent.class, "getExerciseComponentType");
        when(saved.getExerciseComponentType()).thenAnswer(inv -> ecTypeConst);
        when(saved.getExerciseComponentDescription()).thenReturn("d");
        when(saved.getOrderNumber()).thenReturn(1);
        when(ecRepo.save(any(ExerciseComponent.class))).thenReturn(saved);

        PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.createExerciseComponent(anyString(), anyString(), any()))
                .thenReturn(Mono.empty());

        CreateExerciseComponentDTO dto = mock(CreateExerciseComponentDTO.class);
        when(dto.getExerciseId()).thenReturn("EX");
        Object dtoEnumConst = firstEnumConstantFromGetter(CreateExerciseComponentDTO.class, "getExerciseComponentType");
        when(dto.getExerciseComponentType()).thenAnswer(inv -> dtoEnumConst);
        when(dto.getExerciseComponentDescription()).thenReturn("d");

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("n.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("ok".getBytes(StandardCharsets.UTF_8)));
        when(file.getBytes()).thenReturn("bytes".getBytes(StandardCharsets.UTF_8));

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.createExerciseComponentWithFile(dto, file, "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(exercise, "TH"));
        }

        verify(ecRepo).save(any(ExerciseComponent.class));
        verify(PatientAppAPIs.coachExerciseControllerPatientAPI).createExerciseComponent(eq("P"), eq("EX"), any());
    }

    @Test
    void createExerciseComponentWithFile_brokenStream_fallbackToParserUtil() throws Exception {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        Exercise exercise = mock(Exercise.class);
        List<ExerciseComponent> list = new ArrayList<>();
        when(exercise.getExerciseComponents()).thenReturn(list);
        Patient pat = mock(Patient.class);
        when(pat.getId()).thenReturn("P");
        when(exercise.getPatient()).thenReturn(pat);
        when(eRepo.getReferenceById("EX")).thenReturn(exercise);

        ExerciseComponent saved = mock(ExerciseComponent.class);
        when(saved.getId()).thenReturn("EC2");
        Object ecTypeConst = firstEnumConstantFromGetter(ExerciseComponent.class, "getExerciseComponentType");
        when(saved.getExerciseComponentType()).thenAnswer(inv -> ecTypeConst);
        when(saved.getExerciseComponentDescription()).thenReturn("d");
        when(saved.getOrderNumber()).thenReturn(1);
        when(ecRepo.save(any(ExerciseComponent.class))).thenReturn(saved);

        PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.createExerciseComponent(anyString(), anyString(), any()))
                .thenReturn(Mono.empty());

        CreateExerciseComponentDTO dto = mock(CreateExerciseComponentDTO.class);
        when(dto.getExerciseId()).thenReturn("EX");
        Object dtoEnumConst = firstEnumConstantFromGetter(CreateExerciseComponentDTO.class, "getExerciseComponentType");
        when(dto.getExerciseComponentType()).thenAnswer(inv -> dtoEnumConst);
        when(dto.getExerciseComponentDescription()).thenReturn("d");

        MultipartFile file = mock(MultipartFile.class);
        InputStream bad = new InputStream() { @Override public int read() throws IOException { throw new IOException("bad"); } };
        when(file.getOriginalFilename()).thenReturn("bad.pdf");
        when(file.getContentType()).thenReturn("application/pdf");
        when(file.getInputStream()).thenReturn(bad);
        when(file.getBytes()).thenReturn("bytes".getBytes(StandardCharsets.UTF_8));

        try (MockedStatic<DocumentParserUtil> dpi = mockStatic(DocumentParserUtil.class);
             MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            dpi.when(() -> DocumentParserUtil.extractText(file)).thenReturn("ok");
            svc.createExerciseComponentWithFile(dto, file, "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(exercise, "TH"));
        }

        verify(ecRepo).save(any(ExerciseComponent.class));
        verify(PatientAppAPIs.coachExerciseControllerPatientAPI).createExerciseComponent(eq("P"), eq("EX"), any());
    }

    @Test
    void createExerciseComponentWithFile_fileBytesFail_throwsBadRequest() throws Exception {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        Exercise exercise = mock(Exercise.class);
        List<ExerciseComponent> list = new ArrayList<>();
        when(exercise.getExerciseComponents()).thenReturn(list);
        Patient pat = mock(Patient.class);
        when(exercise.getPatient()).thenReturn(pat);
        when(eRepo.getReferenceById("EX")).thenReturn(exercise);

        CreateExerciseComponentDTO dto = mock(CreateExerciseComponentDTO.class);
        when(dto.getExerciseId()).thenReturn("EX");
        Object dtoEnumConst = firstEnumConstantFromGetter(CreateExerciseComponentDTO.class, "getExerciseComponentType");
        when(dto.getExerciseComponentType()).thenAnswer(inv -> dtoEnumConst);

        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("ok".getBytes(StandardCharsets.UTF_8)));
        when(file.getOriginalFilename()).thenReturn("n");
        when(file.getContentType()).thenReturn("t");
        when(file.getBytes()).thenThrow(new IOException("x"));

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> svc.createExerciseComponentWithFile(dto, file, "TH"));
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            sec.verify(() -> SecurityUtil.checkOwnership(exercise, "TH"));
        }
    }

    @Test
    void getAndDownloadExerciseComponent_callsSecurityAndReturns() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        ExerciseComponent ec = mock(ExerciseComponent.class);
        when(ecRepo.getReferenceById("ID")).thenReturn(ec);
        when(ec.getFileData()).thenReturn(new byte[]{1});

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            assertSame(ec, svc.getExerciseComponent("ID", "TH"));
            assertSame(ec, svc.downloadExerciseComponent("ID", "TH"));
            sec.verify(() -> SecurityUtil.checkOwnership(ec, "TH"), times(2));
        }
    }

    @Test
    void updateExerciseComponent_noReorder_setsFieldsAndCallsApi() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        ExerciseComponent target = mock(ExerciseComponent.class);
        when(target.getOrderNumber()).thenReturn(2);
        when(target.getId()).thenReturn("T");
        Exercise ex = mock(Exercise.class);
        List<ExerciseComponent> list = new ArrayList<>();
        list.add(target);
        when(ex.getExerciseComponents()).thenReturn(list);
        when(target.getExercise()).thenReturn(ex);
        Patient pat = mock(Patient.class);
        when(pat.getId()).thenReturn("P");
        when(ex.getPatient()).thenReturn(pat);
        when(ex.getId()).thenReturn("EX");
        when(ecRepo.getReferenceById("T")).thenReturn(target);

        PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.updateExerciseComponent(anyString(), anyString(), anyString(), any(ExerciseComponentUpdateInputDTOPatientAPI.class)))
                .thenReturn(Mono.empty());

        UpdateExerciseComponentDTO dto = new UpdateExerciseComponentDTO();
        dto.setId("T");
        dto.setExerciseComponentDescription("d");
        dto.setYoutubeUrl("y");
        dto.setOrderNumber(2);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.updateExerciseComponent(dto, "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(target, "TH"));
            sec.verify(() -> SecurityUtil.checkOwnership(ex, "TH"));
        }

        verify(target).setExerciseComponentDescription("d");
        verify(target).setYoutubeUrl("y");
        verify(ecRepo, atLeastOnce()).save(target);
        verify(PatientAppAPIs.coachExerciseControllerPatientAPI).updateExerciseComponent(eq("P"), eq("EX"), eq("T"), any());
    }

    @Test
    void updateExerciseComponent_reorder_moveUp() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        ExerciseComponent a = mock(ExerciseComponent.class);
        when(a.getId()).thenReturn("A");
        when(a.getOrderNumber()).thenReturn(1);
        ExerciseComponent b = mock(ExerciseComponent.class);
        when(b.getId()).thenReturn("B");
        when(b.getOrderNumber()).thenReturn(2);
        ExerciseComponent c = mock(ExerciseComponent.class);
        when(c.getId()).thenReturn("C");
        when(c.getOrderNumber()).thenReturn(3);

        Exercise ex = mock(Exercise.class);
        List<ExerciseComponent> list = new ArrayList<>(List.of(a, b, c));
        when(ex.getExerciseComponents()).thenReturn(list);
        when(ex.getId()).thenReturn("EX");
        Patient p = mock(Patient.class);
        when(p.getId()).thenReturn("P");
        when(ex.getPatient()).thenReturn(p);

        when(b.getExercise()).thenReturn(ex);
        when(ecRepo.getReferenceById("B")).thenReturn(b);
        when(b.getOrderNumber()).thenReturn(3);

        PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.updateExerciseComponent(anyString(), anyString(), anyString(), any()))
                .thenReturn(Mono.empty());

        UpdateExerciseComponentDTO dto = new UpdateExerciseComponentDTO();
        dto.setId("B");
        dto.setOrderNumber(1);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.updateExerciseComponent(dto, "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(b, "TH"));
            sec.verify(() -> SecurityUtil.checkOwnership(ex, "TH"));
        }

        verify(a).setOrderNumber(2);
        verify(ecRepo, atLeastOnce()).save(a);
        verify(b).setOrderNumber(1);
        verify(PatientAppAPIs.coachExerciseControllerPatientAPI, atLeastOnce()).updateExerciseComponent(eq("P"), eq("EX"), eq("A"), any());
        verify(PatientAppAPIs.coachExerciseControllerPatientAPI).updateExerciseComponent(eq("P"), eq("EX"), eq("B"), any());
    }

    @Test
    void updateExerciseComponent_reorder_moveDown() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        ExerciseComponent a = mock(ExerciseComponent.class);
        when(a.getId()).thenReturn("A");
        when(a.getOrderNumber()).thenReturn(1);
        ExerciseComponent b = mock(ExerciseComponent.class);
        when(b.getId()).thenReturn("B");
        when(b.getOrderNumber()).thenReturn(2);
        ExerciseComponent c = mock(ExerciseComponent.class);
        when(c.getId()).thenReturn("C");
        when(c.getOrderNumber()).thenReturn(3);

        Exercise ex = mock(Exercise.class);
        List<ExerciseComponent> list = new ArrayList<>(List.of(a, b, c));
        when(ex.getExerciseComponents()).thenReturn(list);
        when(ex.getId()).thenReturn("EX");
        Patient p = mock(Patient.class);
        when(p.getId()).thenReturn("P");
        when(ex.getPatient()).thenReturn(p);

        when(a.getExercise()).thenReturn(ex);
        when(ecRepo.getReferenceById("A")).thenReturn(a);
        when(a.getOrderNumber()).thenReturn(1);

        PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.updateExerciseComponent(anyString(), anyString(), anyString(), any()))
                .thenReturn(Mono.empty());

        UpdateExerciseComponentDTO dto = new UpdateExerciseComponentDTO();
        dto.setId("A");
        dto.setOrderNumber(3);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.updateExerciseComponent(dto, "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(a, "TH"));
            sec.verify(() -> SecurityUtil.checkOwnership(ex, "TH"));
        }

        verify(b).setOrderNumber(1);
        verify(c).setOrderNumber(2);
        verify(a).setOrderNumber(3);
        verify(PatientAppAPIs.coachExerciseControllerPatientAPI, atLeastOnce()).updateExerciseComponent(eq("P"), eq("EX"), anyString(), any());
        verify(ecRepo, atLeastOnce()).save(any());
    }

    @Test
    void deleteExerciseComponent_updatesOrdersAndDeletes() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        ExerciseComponent del = mock(ExerciseComponent.class);
        when(del.getOrderNumber()).thenReturn(2);
        when(del.getId()).thenReturn("DEL");

        ExerciseComponent a = mock(ExerciseComponent.class);
        when(a.getId()).thenReturn("A");
        when(a.getOrderNumber()).thenReturn(1);
        ExerciseComponent c = mock(ExerciseComponent.class);
        when(c.getId()).thenReturn("C");
        when(c.getOrderNumber()).thenReturn(3);

        Exercise ex = mock(Exercise.class);
        List<ExerciseComponent> comps = new ArrayList<>(List.of(a, del, c));
        when(ex.getExerciseComponents()).thenReturn(comps);
        when(del.getExercise()).thenReturn(ex);
        when(ex.getId()).thenReturn("EX");
        Patient p = mock(Patient.class);
        when(p.getId()).thenReturn("P");
        when(ex.getPatient()).thenReturn(p);

        when(ecRepo.findById("DEL")).thenReturn(Optional.of(del));

        PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.updateExerciseComponent(anyString(), anyString(), anyString(), any()))
                .thenReturn(Mono.empty());
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.deleteExerciseComponent(anyString(), anyString(), anyString()))
                .thenReturn(Mono.empty());

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.deleteExerciseComponent("DEL", "TH");
            sec.verify(() -> SecurityUtil.checkOwnership(del, "TH"));
        }

        verify(c).setOrderNumber(2);
        verify(ecRepo).save(c);
        verify(PatientAppAPIs.coachExerciseControllerPatientAPI).updateExerciseComponent(eq("P"), eq("EX"), eq("C"), any());
        verify(PatientAppAPIs.coachExerciseControllerPatientAPI).deleteExerciseComponent("P", "EX", "DEL");
        verify(ecRepo).delete(del);
        verify(eRepo).save(ex);
        verify(ecRepo).flush();
        assertFalse(comps.contains(del));
    }

    @Test
    void deleteExerciseComponent_notFound_throws() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        when(ecRepo.findById("X")).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> svc.deleteExerciseComponent("X", "TH"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
