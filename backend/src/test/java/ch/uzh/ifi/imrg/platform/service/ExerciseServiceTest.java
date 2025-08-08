package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.ExerciseRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseDTO;
import ch.uzh.ifi.imrg.platform.utils.DateUtil;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import ch.uzh.ifi.imrg.platform.utils.ValidationUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @Mock
    ExerciseRepository exerciseRepository;
    @Mock
    PatientRepository patientRepository;
    @InjectMocks
    ExerciseService service;

    MockedStatic<SecurityUtil> securityMock;
    MockedStatic<ValidationUtil> validationMock;
    MockedStatic<DateUtil> dateUtilMock;
    CoachExerciseControllerPatientAPI controllerMock;
    Field apiField;

    @BeforeEach
    void setUp() throws Exception {
        securityMock = Mockito.mockStatic(SecurityUtil.class);
        validationMock = Mockito.mockStatic(ValidationUtil.class);
        dateUtilMock = Mockito.mockStatic(DateUtil.class);
        controllerMock = mock(CoachExerciseControllerPatientAPI.class);
        apiField = PatientAppAPIs.class.getDeclaredField("coachExerciseControllerPatientAPI");
        apiField.setAccessible(true);
        apiField.set(null, controllerMock);
    }

    @AfterEach
    void tearDown() {
        securityMock.close();
        validationMock.close();
        dateUtilMock.close();
    }

    @Test
    void createExerciseInvalidDates() {
        CreateExerciseDTO dto = new CreateExerciseDTO();
        dto.setPatientId("p1");
        dto.setExerciseStart(Instant.now());
        dto.setDurationInWeeks(1);
        when(patientRepository.getReferenceById("p1")).thenReturn(new Patient());
        dateUtilMock.when(() -> DateUtil.addAmountOfWeeks(any(), anyInt())).thenReturn(Instant.now());
        validationMock.when(() -> ValidationUtil.assertStartBeforeEnd(any(), any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> service.createExercise(dto, "ther"));
    }

    @Test
    void getExerciseByIdSuccess() {
        Exercise ex = new Exercise();
        ex.setId("x1");
        when(exerciseRepository.getReferenceById("x1")).thenReturn(ex);
        var out = service.getExerciseById("x1", "ther");
        assertEquals("x1", out.getId());
    }

    @Test
    void getExerciseByIdUnauthorized() {
        when(exerciseRepository.getReferenceById("x")).thenReturn(new Exercise());
        securityMock.when(() -> SecurityUtil.checkOwnership(any(), eq("bad"))).thenThrow(new SecurityException());
        assertThrows(SecurityException.class, () -> service.getExerciseById("x", "bad"));
    }

    @Test
    void getAllExercisesOfPatient() {
        Patient p = new Patient();
        Exercise e1 = new Exercise(); e1.setId("a");
        Exercise e2 = new Exercise(); e2.setId("b");
        p.setExercises(Arrays.asList(e1, e2));
        when(patientRepository.getReferenceById("p")).thenReturn(p);
        var list = service.getAllExercisesOfPatient("p", "ther");
        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(d -> d.getId().equals("a")));
    }

    @Test
    void updateExerciseInvalidDates() {
        Exercise ex = new Exercise();
        ex.setId("u3");
        when(exerciseRepository.getReferenceById("u3")).thenReturn(ex);
        validationMock.when(() -> ValidationUtil.assertStartBeforeEnd(any(), any())).thenThrow(new IllegalArgumentException());
        var dto = new UpdateExerciseDTO();
        dto.setId("u3");
        dto.setExerciseStart(Instant.now());
        dto.setExerciseEnd(Instant.now().minusSeconds(10));
        assertThrows(IllegalArgumentException.class, () -> service.updateExercise(dto, "ther"));
    }

    @Test
    void deleteExerciseUnauthorized() {
        when(exerciseRepository.getReferenceById("d2")).thenReturn(new Exercise());
        securityMock.when(() -> SecurityUtil.checkOwnership(any(), eq("bad"))).thenThrow(new SecurityException());
        assertThrows(SecurityException.class, () -> service.deleteExercise("d2", "bad"));
    }
}
