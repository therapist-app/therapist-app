package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.LogOutputDTOPatientAPI.LogTypeEnum;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.LogOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PatientLogServiceTest {

    PatientRepository repo = mock(PatientRepository.class);
    PatientLogService service;
    Patient patient;

    @BeforeEach
    void init() {
        service = new PatientLogService(repo);
        patient = new Patient();
        when(repo.getReferenceById("pid")).thenReturn(patient);
    }

    private static Class<?> controllerType() throws Exception {
        return PatientAppAPIs.class.getDeclaredField("coachLogControllerPatientAPI").getType();
    }

    private static void injectController(Object impl) throws Exception {
        VarHandle vh =
                MethodHandles.privateLookupIn(PatientAppAPIs.class, MethodHandles.lookup())
                        .findStaticVarHandle(
                                PatientAppAPIs.class, "coachLogControllerPatientAPI", controllerType());
        vh.set(impl);
    }

    @Test
    void listAll_apiThrows() throws Exception {
        Class<?> apiClazz = controllerType();
        Object throwingMock =
                Mockito.mock(
                        apiClazz,
                        inv -> {
                            if ("listAll1".equals(inv.getMethod().getName())) {
                                throw new RuntimeException("err");
                            }
                            return null;
                        });
        injectController(throwingMock);

        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            assertThrows(RuntimeException.class, () -> service.listAll("pid", "X", "tid"));
        }
    }

    @Test
    void listAllForPatient_aggregates() throws Exception {
        PatientLogService spy = Mockito.spy(service);
        List<LogOutputDTO> single = List.of(new LogOutputDTO());
        doReturn(single).when(spy).listAll(anyString(), anyString(), anyString());

        List<LogOutputDTO> out = spy.listAllForPatient("pid", "tid");
        assertEquals(Arrays.stream(LogTypeEnum.values()).count(), out.size());
    }
}
