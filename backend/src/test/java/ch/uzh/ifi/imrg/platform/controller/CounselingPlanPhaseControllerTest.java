package ch.uzh.ifi.imrg.platform.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CounselingPlanPhaseControllerTest {

    @Mock
    CounselingPlanPhaseService service;

    @InjectMocks
    CounselingPlanPhaseController controller;

    @Test
    void createCounselingPlanPhase_delegates() {
        CreateCounselingPlanPhaseDTO in = new CreateCounselingPlanPhaseDTO();
        CounselingPlanPhaseOutputDTO out = new CounselingPlanPhaseOutputDTO();
        when(service.createCounselingPlanPhase(in, "T")).thenReturn(out);
        assertSame(out, controller.createCounselingPlanPhase(in, "T"));
        verify(service).createCounselingPlanPhase(in, "T");
    }

    @Test
    void createAIGeneratedPhase_delegates() {
        CreateCounselingPlanPhaseAIGeneratedDTO in = new CreateCounselingPlanPhaseAIGeneratedDTO();
        CreateCounselingPlanPhaseDTO out = new CreateCounselingPlanPhaseDTO();
        when(service.createCounselingPlanPhaseAIGenerated(in, "T")).thenReturn(out);
        assertSame(out, controller.createCounselingPlanPhaseAIGenerated(in, "T"));
        verify(service).createCounselingPlanPhaseAIGenerated(in, "T");
    }

    @Test
    void createAIGeneratedExercise_delegates() {
        CreateCounselingPlanExerciseAIGeneratedDTO in = new CreateCounselingPlanExerciseAIGeneratedDTO();
        CreateExerciseDTO out = new CreateExerciseDTO();
        when(service.createCounselingPlanExerciseAIGenerated(in, "T")).thenReturn(out);
        assertSame(out, controller.createCounselingPlanExerciseAIGenerated(in, "T"));
        verify(service).createCounselingPlanExerciseAIGenerated(in, "T");
    }

    @Test
    void addExercise_delegates() {
        AddExerciseToCounselingPlanPhaseDTO in = new AddExerciseToCounselingPlanPhaseDTO();
        CounselingPlanPhaseOutputDTO out = new CounselingPlanPhaseOutputDTO();
        when(service.addExerciseToCounselingPlanPhase(in, "T")).thenReturn(out);
        assertSame(out, controller.addExerciseToCounselingPlanPhase(in, "T"));
        verify(service).addExerciseToCounselingPlanPhase(in, "T");
    }

    @Test
    void removeExercise_delegates() {
        RemoveExerciseFromCounselingPlanPhaseDTO in = new RemoveExerciseFromCounselingPlanPhaseDTO();
        CounselingPlanPhaseOutputDTO out = new CounselingPlanPhaseOutputDTO();
        when(service.removeExerciseFromCounselingPlanPhase(in, "T")).thenReturn(out);
        assertSame(out, controller.removeExerciseFromCounselingPlanPhase(in, "T"));
        verify(service).removeExerciseFromCounselingPlanPhase(in, "T");
    }

    @Test
    void getById_delegates() {
        CounselingPlanPhaseOutputDTO out = new CounselingPlanPhaseOutputDTO();
        when(service.getCounselingPlanPhaseById("P", "T")).thenReturn(out);
        assertSame(out, controller.getCounselingPlanPhaseById("P", "T"));
        verify(service).getCounselingPlanPhaseById("P", "T");
    }

    @Test
    void update_delegates() {
        UpdateCounselingPlanPhaseDTO in = new UpdateCounselingPlanPhaseDTO();
        CounselingPlanPhaseOutputDTO out = new CounselingPlanPhaseOutputDTO();
        when(service.updateCounselingPlanPhase(in, "T")).thenReturn(out);
        assertSame(out, controller.updateCounselingPlanPhase(in, "T"));
        verify(service).updateCounselingPlanPhase(in, "T");
    }

    @Test
    void delete_delegates() {
        controller.deleteCounselingPlanPhase("P", "T");
        verify(service).deleteCounselingPlanPhase("P", "T");
    }
}
