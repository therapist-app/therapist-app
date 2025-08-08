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
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseGoalOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class CounselingPlanPhaseGoalServiceTest {

    @Mock CounselingPlanPhaseGoalRepository goalRepo;
    @Mock CounselingPlanPhaseRepository phaseRepo;
    @Mock TherapistRepository therapistRepo;

    CounselingPlanPhaseGoalService service;

    @BeforeEach
    void setUp() {
        service = new CounselingPlanPhaseGoalService(goalRepo, phaseRepo, therapistRepo);
    }

    @Test
    void createGoalStoresEntity() {
        CounselingPlanPhase phase = new CounselingPlanPhase();
        when(phaseRepo.getReferenceById("PID")).thenReturn(phase);
        CreateCounselingPlanPhaseGoalDTO in = new CreateCounselingPlanPhaseGoalDTO();
        in.setCounselingPlanPhaseId("PID");
        in.setGoalName("n");
        in.setGoalDescription("d");
        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            CounselingPlanPhaseGoalOutputDTO out = service.createCounselingPlanPhaseGoal(in, "T");
            assertEquals("n", out.getGoalName());
            verify(goalRepo).save(any(CounselingPlanPhaseGoal.class));
            verify(goalRepo).flush();
        }
    }

    @Test
    void createAIGeneratedSuccess() {
        CounselingPlanPhase phase = mock(CounselingPlanPhase.class);
        when(phaseRepo.findById("PID")).thenReturn(Optional.of(phase));
        when(phase.getId()).thenReturn("PID");
        when(phase.getPhaseName()).thenReturn("PN");
        CounselingPlan plan = mock(CounselingPlan.class);
        Patient patient = mock(Patient.class);
        when(patient.toLLMContext(0)).thenReturn("CTX");
        when(plan.getPatient()).thenReturn(patient);
        when(phase.getCounselingPlan()).thenReturn(plan);

        Therapist therapist = new Therapist();
        therapist.setLlmModel(LLMModel.LOCAL_UZH);
        when(therapistRepo.getReferenceById("T")).thenReturn(therapist);

        LLM llm = mock(LLM.class);
        when(llm.callLLMForObject(anyList(), eq(CreateCounselingPlanPhaseGoalDTO.class), eq(Language.English)))
                .thenAnswer(inv -> {
                    CreateCounselingPlanPhaseGoalDTO dto = new CreateCounselingPlanPhaseGoalDTO();
                    dto.setGoalName("GN");
                    return dto;
                });

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class);
             MockedStatic<LLMFactory> fact = mockStatic(LLMFactory.class)) {

            fact.when(() -> LLMFactory.getInstance(LLMModel.LOCAL_UZH)).thenReturn(llm);

            CreateCounselingPlanPhaseGoalAIGeneratedDTO in = new CreateCounselingPlanPhaseGoalAIGeneratedDTO();
            in.setCounselingPlanPhaseId("PID");
            in.setLanguage(Language.English);

            CreateCounselingPlanPhaseGoalDTO out = service.createCounselingPlanPhaseGoalAIGenerated(in, "T");
            assertEquals("PID", out.getCounselingPlanPhaseId());
            assertEquals("GN", out.getGoalName());
        }
    }

    @Test
    void createAIGeneratedNotFound() {
        when(phaseRepo.findById("X")).thenReturn(Optional.empty());
        CreateCounselingPlanPhaseGoalAIGeneratedDTO in = new CreateCounselingPlanPhaseGoalAIGeneratedDTO();
        in.setCounselingPlanPhaseId("X");
        in.setLanguage(Language.English);
        assertThrows(ResponseStatusException.class,
                () -> service.createCounselingPlanPhaseGoalAIGenerated(in, "T"));
    }

    @Test
    void updateBranches() {
        CounselingPlanPhaseGoal goal = new CounselingPlanPhaseGoal();
        goal.setGoalName("A");
        goal.setGoalDescription("B");
        goal.setIsCompleted(false);
        when(goalRepo.getReferenceById("G")).thenReturn(goal);

        UpdateCounselingPlanPhaseGoalDTO dto1 = new UpdateCounselingPlanPhaseGoalDTO();
        dto1.setCounselingPlanPhaseGoalId("G");
        dto1.setGoalName("A1");
        dto1.setGoalDescription("B1");
        dto1.setIsCompleted(true);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            CounselingPlanPhaseGoalOutputDTO out1 = service.updateCounselingPlanPhaseGoal(dto1, "T");
            assertEquals("A1", out1.getGoalName());
            assertTrue(out1.getIsCompleted());
            verify(goalRepo).save(goal);
        }

        UpdateCounselingPlanPhaseGoalDTO dto2 = new UpdateCounselingPlanPhaseGoalDTO();
        dto2.setCounselingPlanPhaseGoalId("G");
        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            CounselingPlanPhaseGoalOutputDTO out2 = service.updateCounselingPlanPhaseGoal(dto2, "T");
            assertEquals("A1", out2.getGoalName());
            assertTrue(out2.getIsCompleted());
        }
    }

    @Test
    void deleteCallsRemove() {
        CounselingPlanPhaseGoal goal = mock(CounselingPlanPhaseGoal.class);
        CounselingPlanPhase phase = mock(CounselingPlanPhase.class);
        when(goal.getCounselingPlanPhase()).thenReturn(phase);
        when(phase.getPhaseGoals()).thenReturn(new ArrayList<>(List.of(goal)));
        when(goalRepo.getReferenceById("G")).thenReturn(goal);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            service.deleteCounselingPlanPhaseGoal("G", "T");
            assertTrue(phase.getPhaseGoals().isEmpty());
        }
    }
}
