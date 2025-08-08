package ch.uzh.ifi.imrg.platform.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.repository.*;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ExerciseComponentServiceTest {

    @Mock ExerciseComponentRepository ecRepo;
    @Mock ExerciseRepository exRepo;
    @Mock TherapistRepository thRepo;

    ExerciseComponentService service;

    @BeforeEach
    void init() throws Exception {
        service = new ExerciseComponentService(ecRepo, exRepo, thRepo);

        Field f = PatientAppAPIs.class.getDeclaredField("coachExerciseControllerPatientAPI");
        f.setAccessible(true);
        Class<?> apiType = f.getType();

        Object apiMock = Mockito.mock(apiType, invocation -> Mono.empty());
        f.set(null, apiMock);
    }

    @Test
    void deleteNotFound() {
        when(ecRepo.findById("X")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> service.deleteExerciseComponent("X", "T"));
    }
}
