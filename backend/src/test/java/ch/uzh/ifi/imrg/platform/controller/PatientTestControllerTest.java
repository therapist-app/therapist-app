package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestCreateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PsychologicalTestOutputDTO;
import ch.uzh.ifi.imrg.platform.service.PatientTestService;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientTestControllerTest {

    @Test
    void endpoints_delegate_to_service() {
        PatientTestService svc = mock(PatientTestService.class);
        PatientTestController ctrl = new PatientTestController(svc);

        PsychologicalTestOutputDTO out = mock(PsychologicalTestOutputDTO.class);
        when(svc.getTestsByPatient("pid", "tid", "NAME")).thenReturn(List.of(out));
        assertEquals(1, ctrl.getTestsForPatient("pid", "tid", "NAME").size());
        verify(svc).getTestsByPatient("pid", "tid", "NAME");

        PsychologicalTestCreateDTO cfg = new PsychologicalTestCreateDTO();
        when(svc.assignPsychologicalTest("pid", "tid", cfg, "NAME")).thenReturn(cfg);
        assertSame(cfg, ctrl.assignPsychologicalTestToPatient("pid", cfg, "tid", "NAME"));
        verify(svc).assignPsychologicalTest("pid", "tid", cfg, "NAME");

        when(svc.updatePsychologicalTestConfig("pid", "tid", cfg, "NAME")).thenReturn(cfg);
        assertSame(cfg, ctrl.updatePsychologicalTestToPatient("pid", cfg, "tid", "NAME"));
        verify(svc).updatePsychologicalTestConfig("pid", "tid", cfg, "NAME");

        when(svc.getPsychologicalTestConfig("pid", "tid", "NAME")).thenReturn(cfg);
        assertSame(cfg, ctrl.getPsychologicalTestToPatient("pid", "tid", "NAME"));
        verify(svc).getPsychologicalTestConfig("pid", "tid", "NAME");
    }
}
