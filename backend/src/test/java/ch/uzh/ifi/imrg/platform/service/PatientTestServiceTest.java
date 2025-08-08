package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestCreateDTO;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientTestServiceTest {

    PatientRepository repo = mock(PatientRepository.class);
    PatientTestService service;
    Patient patient;

    @BeforeEach
    void init() {
        service = new PatientTestService(repo);
        patient = new Patient();
        when(repo.getReferenceById("pid")).thenReturn(patient);
    }

    private static Class<?> apiClass() throws Exception {
        return PatientAppAPIs.class
                .getDeclaredField("coachPsychologicalTestControllerPatientAPI")
                .getType();
    }

    private static void injectApi(Object impl) throws Exception {
        VarHandle vh =
                MethodHandles.privateLookupIn(PatientAppAPIs.class, MethodHandles.lookup())
                        .findStaticVarHandle(
                                PatientAppAPIs.class,
                                "coachPsychologicalTestControllerPatientAPI",
                                apiClass());
        vh.set(impl);
    }

    @Test
    void getTests_apiThrows() throws Exception {
        Object api =
                Mockito.mock(
                        apiClass(),
                        m -> {
                            if (m.getMethod().getName().equals("getPsychologicalTestResults1"))
                                throw new RuntimeException();
                            return Mono.empty();
                        });
        injectApi(api);

        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            assertThrows(
                    ResponseStatusException.class,
                    () -> service.getTestsByPatient("pid", "tid", "X"));
        }
    }

    private static PsychologicalTestCreateDTO dto() {
        PsychologicalTestCreateDTO d = new PsychologicalTestCreateDTO();
        d.setPatientId("pid");
        d.setTestName("NAME");
        return d;
    }

    @Test
    void assign_update_success_and_fail() throws Exception {
        Object apiOk =
                Mockito.mock(
                        apiClass(),
                        RETURNS_DEEP_STUBS);
        injectApi(apiOk);

        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            assertNotNull(service.assignPsychologicalTest("pid", "tid", dto(), "NAME"));
            assertNotNull(service.updatePsychologicalTestConfig("pid", "tid", dto(), "NAME"));
        }

        Object apiErr =
                Mockito.mock(
                        apiClass(),
                        m -> {
                            if (m.getMethod().getName().startsWith("create")
                                    || m.getMethod().getName().startsWith("update"))
                                throw new RuntimeException();
                            return Mono.empty();
                        });
        injectApi(apiErr);

        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            assertThrows(
                    ResponseStatusException.class,
                    () -> service.assignPsychologicalTest("pid", "tid", dto(), "NAME"));
            assertThrows(
                    ResponseStatusException.class,
                    () -> service.updatePsychologicalTestConfig("pid", "tid", dto(), "NAME"));
        }
    }
}
