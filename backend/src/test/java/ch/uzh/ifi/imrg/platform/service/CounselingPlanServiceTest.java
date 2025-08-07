package ch.uzh.ifi.imrg.platform.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateCounselingPlanDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CounselingPlanServiceTest {

    private final PatientRepository patientRepository = mock(PatientRepository.class);
    private final CounselingPlanRepository counselingPlanRepository = mock(CounselingPlanRepository.class);
    private final CounselingPlanService service =
            new CounselingPlanService(patientRepository, counselingPlanRepository);

    @Test
    void getCounselingPlanByPatientIdReturnsDtoWithEmptyPhases() {
        CounselingPlan plan = mock(CounselingPlan.class);
        when(plan.getCounselingPlanPhases()).thenReturn(Collections.emptyList());

        Patient patient = mock(Patient.class);
        when(patient.getCounselingPlan()).thenReturn(plan);
        when(patientRepository.getReferenceById("p")).thenReturn(patient);

        try (MockedStatic<SecurityUtil> suppressed = mockStatic(SecurityUtil.class)) {
            var result = service.getCounselingPlanByPatientId("p", "t");

            assertNotNull(result);
            assertTrue(result.getCounselingPlanPhasesOutputDTO().isEmpty());
            verify(patientRepository).getReferenceById("p");
            suppressed.verify(() -> SecurityUtil.checkOwnership(plan, "t"));
        }
    }

    @Test
    void updateCounselingPlanWithStartDateSetsStartAndMapsPhase() {
        CounselingPlan plan = mock(CounselingPlan.class);
        CounselingPlanPhase phase = mock(CounselingPlanPhase.class);
        when(plan.getCounselingPlanPhases()).thenReturn(List.of(phase));
        when(counselingPlanRepository.getReferenceById("c")).thenReturn(plan);

        UpdateCounselingPlanDTO dto = mock(UpdateCounselingPlanDTO.class);
        when(dto.getCounselingPlanId()).thenReturn("c");
        Instant start = Instant.now();
        when(dto.getStartOfTherapy()).thenReturn(start);

        CounselingPlanPhaseOutputDTO phaseDto = new CounselingPlanPhaseOutputDTO();

        try (MockedStatic<SecurityUtil> suppressed = mockStatic(SecurityUtil.class);
             MockedStatic<CounselingPlanPhaseService> phaseMapper =
                     mockStatic(CounselingPlanPhaseService.class)) {

            phaseMapper.when(() -> CounselingPlanPhaseService.getOutputDto(phase, plan))
                    .thenReturn(phaseDto);

            var result = service.updateCounselingPlan(dto, "t");

            assertNotNull(result);
            assertEquals(List.of(phaseDto), result.getCounselingPlanPhasesOutputDTO());

            verify(plan).setStartOfTherapy(start);
            verify(counselingPlanRepository).save(plan);
            suppressed.verify(() -> SecurityUtil.checkOwnership(plan, "t"));
        }
    }

    @Test
    void updateCounselingPlanWithoutStartDateLeavesStartUnchanged() {
        CounselingPlan plan = mock(CounselingPlan.class);
        when(plan.getCounselingPlanPhases()).thenReturn(Collections.emptyList());
        when(counselingPlanRepository.getReferenceById("c")).thenReturn(plan);

        UpdateCounselingPlanDTO dto = mock(UpdateCounselingPlanDTO.class);
        when(dto.getCounselingPlanId()).thenReturn("c");
        when(dto.getStartOfTherapy()).thenReturn(null);

        try (MockedStatic<SecurityUtil> suppressed = mockStatic(SecurityUtil.class)) {
            var result = service.updateCounselingPlan(dto, "t");

            assertNotNull(result);
            assertTrue(result.getCounselingPlanPhasesOutputDTO().isEmpty());
            verify(plan, never()).setStartOfTherapy(any());
            verify(counselingPlanRepository).save(plan);
            suppressed.verify(() -> SecurityUtil.checkOwnership(plan, "t"));
        }
    }
}
