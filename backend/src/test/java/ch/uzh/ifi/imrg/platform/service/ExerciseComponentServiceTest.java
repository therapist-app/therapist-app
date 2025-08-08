package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.ExerciseComponentUpdateInputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.ExerciseComponentRepository;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseComponentDTO;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ExerciseComponentServiceTest {

    @Test
    void getAndDownloadExerciseComponent() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        ExerciseComponent ec = mock(ExerciseComponent.class);
        when(ecRepo.getReferenceById("ID")).thenReturn(ec);
        when(ec.getFileData()).thenReturn(new byte[]{1,2});

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
        when(target.getId()).thenReturn("EC");

        Exercise ex = mock(Exercise.class);
        when(ex.getExerciseComponents()).thenReturn(List.of(target));
        when(ex.getId()).thenReturn("EX");
        Patient p = mock(Patient.class);
        when(p.getId()).thenReturn("P");
        when(ex.getPatient()).thenReturn(p);

        when(ecRepo.getReferenceById("EC")).thenReturn(target);
        when(target.getExercise()).thenReturn(ex);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
            when(PatientAppAPIs.coachExerciseControllerPatientAPI.updateExerciseComponent(anyString(), anyString(), anyString(), any(ExerciseComponentUpdateInputDTOPatientAPI.class)))
                    .thenReturn(Mono.empty());

            UpdateExerciseComponentDTO dto = new UpdateExerciseComponentDTO();
            dto.setId("EC");
            dto.setExerciseComponentDescription("d");
            dto.setYoutubeUrl("y");
            dto.setOrderNumber(2);

            svc.updateExerciseComponent(dto, "TH");

            verify(target).setExerciseComponentDescription("d");
            verify(target).setYoutubeUrl("y");
            verify(ecRepo, atLeastOnce()).save(target);
        }
    }

    @Test
    void deleteExerciseComponent_notFound() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        when(ecRepo.findById("X")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> svc.deleteExerciseComponent("X", "TH"));
        try {
            svc.deleteExerciseComponent("X", "TH");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    void deleteExerciseComponent_happyPath_updatesOrdersAndDeletes() {
        ExerciseComponentRepository ecRepo = mock(ExerciseComponentRepository.class);
        ExerciseRepository eRepo = mock(ExerciseRepository.class);
        TherapistRepository tRepo = mock(TherapistRepository.class);
        ExerciseComponentService svc = new ExerciseComponentService(ecRepo, eRepo, tRepo);

        ExerciseComponent toDelete = mock(ExerciseComponent.class);
        when(toDelete.getOrderNumber()).thenReturn(2);
        when(toDelete.getId()).thenReturn("EC2");

        ExerciseComponent ec1 = mock(ExerciseComponent.class);
        when(ec1.getId()).thenReturn("EC1");
        when(ec1.getOrderNumber()).thenReturn(1);

        ExerciseComponent ec3 = mock(ExerciseComponent.class);
        when(ec3.getId()).thenReturn("EC3");
        when(ec3.getOrderNumber()).thenReturn(3);

        Exercise ex = mock(Exercise.class);
        List<ExerciseComponent> components = new ArrayList<>(List.of(ec1, toDelete, ec3));
        when(ex.getExerciseComponents()).thenReturn(components);
        when(toDelete.getExercise()).thenReturn(ex);
        Patient p = mock(Patient.class);
        when(p.getId()).thenReturn("P");
        when(ex.getPatient()).thenReturn(p);
        when(ex.getId()).thenReturn("EX");

        when(ecRepo.findById("EC2")).thenReturn(Optional.of(toDelete));

        PatientAppAPIs.coachExerciseControllerPatientAPI = mock(ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI.class);
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.updateExerciseComponent(anyString(), anyString(), anyString(), any()))
                .thenReturn(Mono.empty());
        when(PatientAppAPIs.coachExerciseControllerPatientAPI.deleteExerciseComponent(anyString(), anyString(), anyString()))
                .thenReturn(Mono.empty());

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            svc.deleteExerciseComponent("EC2", "TH");

            verify(ec3).setOrderNumber(2);
            verify(ecRepo).save(ec3);
            verify(PatientAppAPIs.coachExerciseControllerPatientAPI).updateExerciseComponent(eq("P"), eq("EX"), eq("EC3"), any());
            verify(PatientAppAPIs.coachExerciseControllerPatientAPI).deleteExerciseComponent("P", "EX", "EC2");
            verify(ecRepo).delete(toDelete);
            verify(eRepo).save(ex);
            verify(ecRepo).flush();
        }
    }
}
