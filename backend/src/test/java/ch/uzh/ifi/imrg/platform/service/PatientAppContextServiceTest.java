package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.api.CoachChatbotControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachJournalEntryControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachLogControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachPsychologicalTestControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.model.*;
import ch.uzh.ifi.imrg.platform.constant.LogTypes;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PatientAppContextServiceTest {

    private CoachPsychologicalTestControllerPatientAPI psychApi;
    private CoachExerciseControllerPatientAPI exerciseApi;
    private CoachChatbotControllerPatientAPI chatbotApi;
    private CoachJournalEntryControllerPatientAPI journalApi;
    private CoachLogControllerPatientAPI logApi;

    @BeforeEach
    void setup() {
        psychApi = mock(CoachPsychologicalTestControllerPatientAPI.class);
        exerciseApi = mock(CoachExerciseControllerPatientAPI.class);
        chatbotApi = mock(CoachChatbotControllerPatientAPI.class);
        journalApi = mock(CoachJournalEntryControllerPatientAPI.class);
        logApi = mock(CoachLogControllerPatientAPI.class);

        PatientAppAPIs.coachPsychologicalTestControllerPatientAPI = psychApi;
        PatientAppAPIs.coachExerciseControllerPatientAPI = exerciseApi;
        PatientAppAPIs.coachChatbotControllerPatientAPI = chatbotApi;
        PatientAppAPIs.coachJournalEntryControllerPatientAPI = journalApi;
        PatientAppAPIs.coachLogControllerPatientAPI = logApi;
    }

    @AfterEach
    void tearDown() {
        PatientAppAPIs.coachPsychologicalTestControllerPatientAPI = null;
        PatientAppAPIs.coachExerciseControllerPatientAPI = null;
        PatientAppAPIs.coachChatbotControllerPatientAPI = null;
        PatientAppAPIs.coachJournalEntryControllerPatientAPI = null;
        PatientAppAPIs.coachLogControllerPatientAPI = null;
    }

    @Test
    void buildContext_fullSections() {
        String pid = "p1";

        PsychologicalTestOutputDTOPatientAPI p1 = new PsychologicalTestOutputDTOPatientAPI();
        p1.setCompletedAt(Instant.now().minusSeconds(100));
        p1.setQuestions(new ArrayList<>());
        PsychologicalTestOutputDTOPatientAPI p2 = new PsychologicalTestOutputDTOPatientAPI();
        p2.setCompletedAt(Instant.now());
        p2.setQuestions(new ArrayList<>());
        when(psychApi.getPsychologicalTestResults1(anyString(), anyString()))
                .thenReturn(Flux.just(p1, p2));

        ExercisesOverviewOutputDTOPatientAPI ex1 = new ExercisesOverviewOutputDTOPatientAPI();
        ex1.setId("exA");
        when(exerciseApi.getAllExercises(eq(pid))).thenReturn(Flux.just(ex1));
        ExerciseInformationOutputDTOPatientAPI infoRecent = new ExerciseInformationOutputDTOPatientAPI();
        infoRecent.setEndTime(Instant.now());
        when(exerciseApi.getExerciseInformation(eq(pid), eq("exA")))
                .thenReturn(Flux.just(infoRecent));

        ConversationSummaryOutputDTOPatientAPI convo = new ConversationSummaryOutputDTOPatientAPI();
        convo.setConversationSummary("  Summary text  ");
        when(chatbotApi.getConversationSummary(any(GetConversationSummaryInputDTOPatientAPI.class), eq(pid)))
                .thenReturn(Mono.just(convo));

        CoachGetAllJournalEntriesDTOPatientAPI je = new CoachGetAllJournalEntriesDTOPatientAPI();
        je.setCreatedAt(Instant.now());
        je.setTitle("Entry");
        when(journalApi.listAll2(eq(pid))).thenReturn(Flux.just(je));

        for (LogTypes t : LogTypes.values()) {
            LogOutputDTOPatientAPI log = new LogOutputDTOPatientAPI();
            log.setTimestamp(Instant.now());
            when(logApi.listAll1(eq(pid), eq(t.name()))).thenReturn(Flux.just(log));
        }

        PatientAppContextService svc = new PatientAppContextService();
        String ctx = svc.buildContext(pid);

        assertTrue(ctx.contains("## Client-App Context"));
        assertTrue(ctx.contains("Latest GAD-7"));
        assertTrue(ctx.contains("Exercise Completions"));
        assertTrue(ctx.contains("Chatbot Summary"));
        assertTrue(ctx.contains("Recent Journal Entries"));
        assertTrue(ctx.contains("Log Counts per Week"));
    }

    @Test
    void buildContext_handlesEmptyAndErrors() {
        String pid = "pX";

        when(psychApi.getPsychologicalTestResults1(anyString(), anyString()))
                .thenReturn(Flux.empty());

        when(exerciseApi.getAllExercises(eq(pid))).thenReturn(Flux.empty());
        when(chatbotApi.getConversationSummary(any(), eq(pid))).thenReturn(Mono.just(new ConversationSummaryOutputDTOPatientAPI()));
        when(journalApi.listAll2(eq(pid))).thenReturn(Flux.empty());
        for (LogTypes t : LogTypes.values()) {
            when(logApi.listAll1(eq(pid), eq(t.name()))).thenReturn(Flux.empty());
        }

        PatientAppContextService svc = new PatientAppContextService();
        String ctx = svc.buildContext(pid);

        assertTrue(ctx.contains("## Client-App Context"));
        assertFalse(ctx.contains("Latest GAD-7\n"));
    }
}
