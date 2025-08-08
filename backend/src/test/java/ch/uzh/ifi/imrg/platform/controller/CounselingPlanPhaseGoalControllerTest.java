package ch.uzh.ifi.imrg.platform.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseGoalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CounselingPlanPhaseGoalControllerTest {

    @Mock CounselingPlanPhaseGoalService service;
    @InjectMocks CounselingPlanPhaseGoalController controller;

    @Test
    void createDelegates() {
        CreateCounselingPlanPhaseGoalDTO in = new CreateCounselingPlanPhaseGoalDTO();
        CounselingPlanPhaseGoalOutputDTO out = new CounselingPlanPhaseGoalOutputDTO();
        when(service.createCounselingPlanPhaseGoal(in, "T")).thenReturn(out);
        assertSame(out, controller.createCounselingPlanPhaseGoal(in, "T"));
        verify(service).createCounselingPlanPhaseGoal(in, "T");
    }

    @Test
    void createAIDelegates() {
        CreateCounselingPlanPhaseGoalAIGeneratedDTO in = new CreateCounselingPlanPhaseGoalAIGeneratedDTO();
        CreateCounselingPlanPhaseGoalDTO out = new CreateCounselingPlanPhaseGoalDTO();
        when(service.createCounselingPlanPhaseGoalAIGenerated(in, "T")).thenReturn(out);
        assertSame(out, controller.createCounselingPlanPhaseGoalAIGenerated(in, "T"));
        verify(service).createCounselingPlanPhaseGoalAIGenerated(in, "T");
    }

    @Test
    void getDelegates() {
        CounselingPlanPhaseGoalOutputDTO out = new CounselingPlanPhaseGoalOutputDTO();
        when(service.getCounselingPlanPhaseGoalById("ID", "T")).thenReturn(out);
        assertSame(out, controller.getCounselingPlanPhaseGoalById("ID", "T"));
        verify(service).getCounselingPlanPhaseGoalById("ID", "T");
    }

    @Test
    void updateDelegates() {
        UpdateCounselingPlanPhaseGoalDTO in = new UpdateCounselingPlanPhaseGoalDTO();
        CounselingPlanPhaseGoalOutputDTO out = new CounselingPlanPhaseGoalOutputDTO();
        when(service.updateCounselingPlanPhaseGoal(in, "T")).thenReturn(out);
        assertSame(out, controller.updateCounselingPlanPhaseGoal(in, "T"));
        verify(service).updateCounselingPlanPhaseGoal(in, "T");
    }

    @Test
    void deleteDelegates() {
        controller.deleteCounselingPlanPhaseGoal("ID", "T");
        verify(service).deleteCounselingPlanPhaseGoal("ID", "T");
    }
}
