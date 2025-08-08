package ch.uzh.ifi.imrg.platform.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.LLM.LLM;
import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.entity.*;
import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import ch.uzh.ifi.imrg.platform.repository.*;
import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.DateUtil;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class CounselingPlanPhaseServiceTest {

    @Mock CounselingPlanPhaseRepository phaseRepo;
    @Mock ExerciseRepository exerciseRepo;
    @Mock CounselingPlanRepository planRepo;
    @Mock TherapistRepository therapistRepo;

    CounselingPlanPhaseService service;

    @BeforeEach
    void init() {
        service = new CounselingPlanPhaseService(phaseRepo, exerciseRepo, planRepo, therapistRepo);
    }

    @Test
    void updatePhase_updatesBothFields() {
        CounselingPlan plan = mock(CounselingPlan.class);
        when(plan.getCounselingPlanPhases()).thenReturn(new ArrayList<>());
        when(plan.getStartOfTherapy()).thenReturn(Instant.parse("2025-01-01T00:00:00Z"));
        CounselingPlanPhase phase = new CounselingPlanPhase();
        phase.setCounselingPlan(plan);
        when(phaseRepo.findById("ID")).thenReturn(Optional.of(phase));

        UpdateCounselingPlanPhaseDTO dto = new UpdateCounselingPlanPhaseDTO();
        dto.setCounselingPlanPhaseId("ID");
        dto.setPhaseName("New");
        dto.setDurationInWeeks(8);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class);
             MockedStatic<DateUtil> date = mockStatic(DateUtil.class)) {

            date.when(() -> DateUtil.addAmountOfWeeks(any(), anyInt()))
                    .thenReturn(Instant.parse("2025-01-01T00:00:00Z"));

            CounselingPlanPhaseOutputDTO out = service.updateCounselingPlanPhase(dto, "T");
            assertEquals("New", out.getPhaseName());
            verify(phaseRepo).save(phase);
        }
    }

    @Test
    void updatePhase_keepsValuesWhenNull() {
        CounselingPlan plan = mock(CounselingPlan.class);
        when(plan.getCounselingPlanPhases()).thenReturn(new ArrayList<>());
        when(plan.getStartOfTherapy()).thenReturn(Instant.parse("2025-01-01T00:00:00Z"));
        CounselingPlanPhase phase = new CounselingPlanPhase();
        phase.setPhaseName("Old");
        phase.setDurationInWeeks(4);
        phase.setCounselingPlan(plan);
        when(phaseRepo.findById("ID")).thenReturn(Optional.of(phase));

        UpdateCounselingPlanPhaseDTO dto = new UpdateCounselingPlanPhaseDTO();
        dto.setCounselingPlanPhaseId("ID");

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class);
             MockedStatic<DateUtil> date = mockStatic(DateUtil.class)) {

            date.when(() -> DateUtil.addAmountOfWeeks(any(), anyInt()))
                    .thenReturn(Instant.parse("2025-01-01T00:00:00Z"));

            CounselingPlanPhaseOutputDTO out = service.updateCounselingPlanPhase(dto, "T");
            assertEquals("Old", out.getPhaseName());
            assertEquals(4, out.getDurationInWeeks());
        }
    }

    @Test
    void updatePhase_notFound() {
        when(phaseRepo.findById("X")).thenReturn(Optional.empty());
        UpdateCounselingPlanPhaseDTO dto = new UpdateCounselingPlanPhaseDTO();
        dto.setCounselingPlanPhaseId("X");
        assertThrows(
                ResponseStatusException.class, () -> service.updateCounselingPlanPhase(dto, "T"));
    }

    @Test
    void getOutputDto_calculatesOffsets() {
        CounselingPlan plan = new CounselingPlan();
        Instant start = Instant.parse("2025-01-01T00:00:00Z");
        plan.setStartOfTherapy(start);

        CounselingPlanPhase first = new CounselingPlanPhase();
        first.setDurationInWeeks(4);
        first.setPhaseNumber(1);

        CounselingPlanPhase second = new CounselingPlanPhase();
        second.setDurationInWeeks(6);
        second.setPhaseNumber(2);

        plan.setCounselingPlanPhases(List.of(first, second));
        first.setCounselingPlan(plan);
        second.setCounselingPlan(plan);

        try (MockedStatic<DateUtil> date = mockStatic(DateUtil.class)) {
            date.when(() -> DateUtil.addAmountOfWeeks(start, 0)).thenReturn(start);
            Instant startSecond = Instant.parse("2025-01-29T00:00:00Z");
            date.when(() -> DateUtil.addAmountOfWeeks(start, 4)).thenReturn(startSecond);

            CounselingPlanPhaseOutputDTO dto = CounselingPlanPhaseService.getOutputDto(second, plan);
            assertEquals(startSecond, dto.getStartDate());
        }
    }

    @Test
    void createAIGeneratedPhase_success() {
        CreateCounselingPlanPhaseAIGeneratedDTO in = new CreateCounselingPlanPhaseAIGeneratedDTO();
        in.setCounselingPlanId("P");
        in.setLanguage(Language.English);

        Patient patient = mock(Patient.class);
        when(patient.toLLMContext(0)).thenReturn("CTX");
        CounselingPlan plan = mock(CounselingPlan.class);
        when(plan.getPatient()).thenReturn(patient);
        when(planRepo.findById("P")).thenReturn(Optional.of(plan));

        Therapist therapist = new Therapist();
        therapist.setLlmModel(LLMModel.LOCAL_UZH);
        when(therapistRepo.getReferenceById("T")).thenReturn(therapist);

        LLM llm = mock(LLM.class);
        when(llm.callLLMForObject(
                anyList(), eq(CreateCounselingPlanPhaseDTO.class), eq(Language.English)))
                .thenReturn(new CreateCounselingPlanPhaseDTO());

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class);
             MockedStatic<LLMFactory> llmf = mockStatic(LLMFactory.class)) {

            llmf.when(() -> LLMFactory.getInstance(LLMModel.LOCAL_UZH)).thenReturn(llm);

            CreateCounselingPlanPhaseDTO out =
                    service.createCounselingPlanPhaseAIGenerated(in, "T");
            assertEquals("P", out.getCounselingPlanId());
        }
    }

    @Test
    void createAIGeneratedPhase_notFound() {
        when(planRepo.findById("M")).thenReturn(Optional.empty());
        CreateCounselingPlanPhaseAIGeneratedDTO in = new CreateCounselingPlanPhaseAIGeneratedDTO();
        in.setCounselingPlanId("M");
        assertThrows(
                ResponseStatusException.class,
                () -> service.createCounselingPlanPhaseAIGenerated(in, "T"));
    }

    @Test
    void createAIGeneratedExercise_notFound() {
        when(phaseRepo.findById("X")).thenReturn(Optional.empty());
        CreateCounselingPlanExerciseAIGeneratedDTO in =
                new CreateCounselingPlanExerciseAIGeneratedDTO();
        in.setCounselingPlanPhaseId("X");
        assertThrows(
                ResponseStatusException.class,
                () -> service.createCounselingPlanExerciseAIGenerated(in, "T"));
    }

    @Test
    void getById_notFound() {
        when(phaseRepo.findById("Z")).thenReturn(Optional.empty());
        assertThrows(
                ResponseStatusException.class, () -> service.getCounselingPlanPhaseById("Z", "T"));
    }

    @Test
    void delete_notFound() {
        when(phaseRepo.findById("Z")).thenReturn(Optional.empty());
        assertThrows(
                ResponseStatusException.class, () -> service.deleteCounselingPlanPhase("Z", "T"));
    }
}
