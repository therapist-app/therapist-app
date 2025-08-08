package ch.uzh.ifi.imrg.platform.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.generated.model.ExerciseInformationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateExerciseDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ExerciseOutputDTO;
import ch.uzh.ifi.imrg.platform.service.ExerciseService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ExerciseControllerTest {

    @Mock ExerciseService service;
    ExerciseController controller;

    @BeforeEach
    void set() {
        controller = new ExerciseController(service);
    }

    @Test
    void routesDelegateToService() {
        ExerciseOutputDTO out = new ExerciseOutputDTO();
        when(service.createExercise(any(), eq("T"))).thenReturn(out);
        assertSame(out, controller.createExercise(new CreateExerciseDTO(), "T"));

        when(service.getExerciseById("E", "T")).thenReturn(out);
        assertSame(out, controller.getExerciseById("E", "T"));

        when(service.getAllExercisesOfPatient("P", "T")).thenReturn(List.of(out));
        assertEquals(1, controller.getAllExercisesOfPatient("P", "T").size());

        List<ExerciseInformationOutputDTOPatientAPI> info = List.of(new ExerciseInformationOutputDTOPatientAPI());
        when(service.getExerciseInformation("E", "T")).thenReturn(info);
        assertSame(info, controller.getExerciseInformation("E", "T"));

        when(service.updateExercise(any(), eq("T"))).thenReturn(out);
        assertSame(out, controller.updateExercise(new UpdateExerciseDTO(), "T"));

        controller.deleteExercise("E", "T");
        verify(service).deleteExercise("E", "T");
    }
}
