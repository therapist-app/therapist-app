package ch.uzh.ifi.imrg.platform.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CounselingPlanControllerTest {

    @Mock
    CounselingPlanService counselingPlanService;

    @InjectMocks
    CounselingPlanController controller;

    @Test
    void getCounselingPlanByPatientIdDelegates() {
        CounselingPlanOutputDTO dto = new CounselingPlanOutputDTO();
        when(counselingPlanService.getCounselingPlanByPatientId("p", "t")).thenReturn(dto);
        CounselingPlanOutputDTO result = controller.getCounselingPlanByPatientId("p", "t");
        assertSame(dto, result);
        verify(counselingPlanService).getCounselingPlanByPatientId("p", "t");
    }

    @Test
    void updateCounselingPlanDelegates() {
        UpdateCounselingPlanDTO input = new UpdateCounselingPlanDTO();
        CounselingPlanOutputDTO dto = new CounselingPlanOutputDTO();
        when(counselingPlanService.updateCounselingPlan(input, "t")).thenReturn(dto);
        CounselingPlanOutputDTO result = controller.updateCounselingPlan(input, "t");
        assertSame(dto, result);
        verify(counselingPlanService).updateCounselingPlan(input, "t");
    }
}
