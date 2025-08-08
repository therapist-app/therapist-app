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
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PatientAppContextServiceTest {

    CoachPsychologicalTestControllerPatientAPI prevPsych = PatientAppAPIs.coachPsychologicalTestControllerPatientAPI;
    CoachExerciseControllerPatientAPI prevEx = PatientAppAPIs.coachExerciseControllerPatientAPI;
    CoachChatbotControllerPatientAPI prevChat = PatientAppAPIs.coachChatbotControllerPatientAPI;
    CoachJournalEntryControllerPatientAPI prevJournal = PatientAppAPIs.coachJournalEntryControllerPatientAPI;
    CoachLogControllerPatientAPI prevLog = PatientAppAPIs.coachLogControllerPatientAPI;

    @AfterEach
    void restore() {
        PatientAppAPIs.coachPsychologicalTestControllerPatientAPI = prevPsych;
        PatientAppAPIs.coachExerciseControllerPatientAPI = prevEx;
        PatientAppAPIs.coachChatbotControllerPatientAPI = prevChat;
        PatientAppAPIs.coachJournalEntryControllerPatientAPI = prevJournal;
        PatientAppAPIs.coachLogControllerPatientAPI = prevLog;
    }

    @Test
    void buildContext_fullSectionsFromSuccessfulCalls() {
        CoachPsychologicalTestControllerPatientAPI psych = mock(CoachPsychologicalTestControllerPatientAPI.class);
        when(psych.getPsychologicalTestResults1(anyString(), anyString())).thenReturn(Flux.fromIterable(Collections.emptyList()));
        PatientAppAPIs.coachPsychologicalTestControllerPatientAPI = psych;

        CoachExerciseControllerPatientAPI ex = mock(CoachExerciseControllerPatientAPI.class);
        ExercisesOverviewOutputDTOPatientAPI ex1 = new ExercisesOverviewOutputDTOPatientAPI();
        ex1.setId("e1");
        when(ex.getAllExercises(eq("pid"))).thenReturn(Flux.just(ex1));
        ExerciseInformationOutputDTOPatientAPI info1 = new ExerciseInformationOutputDTOPatientAPI();
        info1.setEndTime(Instant.now());
        when(ex.getExerciseInformation(eq("pid"), eq("e1"))).thenReturn(Flux.just(info1));
        PatientAppAPIs.coachExerciseControllerPatientAPI = ex;

        CoachChatbotControllerPatientAPI chat = mock(CoachChatbotControllerPatientAPI.class);
        ConversationSummaryOutputDTOPatientAPI sum = new ConversationSummaryOutputDTOPatientAPI();
        sum.setConversationSummary("  summary  ");
        when(chat.getConversationSummary(any(GetConversationSummaryInputDTOPatientAPI.class), eq("pid"))).thenReturn(Mono.just(sum));
        PatientAppAPIs.coachChatbotControllerPatientAPI = chat;

        CoachJournalEntryControllerPatientAPI journal = mock(CoachJournalEntryControllerPatientAPI.class);
        CoachGetAllJournalEntriesDTOPatientAPI j1 = new CoachGetAllJournalEntriesDTOPatientAPI();
        j1.setTitle("t1");
        j1.setCreatedAt(Instant.now());
        when(journal.listAll2(eq("pid"))).thenReturn(Flux.just(j1));
        PatientAppAPIs.coachJournalEntryControllerPatientAPI = journal;

        CoachLogControllerPatientAPI log = mock(CoachLogControllerPatientAPI.class);
        LogOutputDTOPatientAPI l1 = new LogOutputDTOPatientAPI();
        l1.setTimestamp(Instant.now());
        LogOutputDTOPatientAPI l2 = new LogOutputDTOPatientAPI();
        l2.setTimestamp(Instant.now());
        for (LogTypes t : LogTypes.values()) {
            when(log.listAll1(eq("pid"), eq(t.name()))).thenReturn(Flux.fromIterable(t == LogTypes.GENERAL_CONVERSATION_CREATION ? List.of(l1, l2) : Collections.emptyList()));
        }
        PatientAppAPIs.coachLogControllerPatientAPI = log;

        String ctx = new PatientAppContextService().buildContext("pid");
        assertTrue(ctx.contains("## Client-App Context"));
        assertTrue(ctx.contains("Exercise Completions"));
        assertTrue(ctx.contains("Chatbot Summary"));
        assertTrue(ctx.contains("Recent Journal Entries"));
        assertTrue(ctx.contains("Log Counts per Week"));
        assertFalse(ctx.contains("Latest GAD-7"));
        assertTrue(ctx.contains("Total completed: 1"));
        assertTrue(ctx.contains("summary"));
        assertTrue(ctx.contains("t1"));
        assertTrue(ctx.contains(LogTypes.GENERAL_CONVERSATION_CREATION.toString()));
    }

    @Test
    void buildContext_handlesEmptyAndExceptions() {
        CoachPsychologicalTestControllerPatientAPI psych = mock(CoachPsychologicalTestControllerPatientAPI.class);
        when(psych.getPsychologicalTestResults1(anyString(), anyString())).thenThrow(new RuntimeException("x"));
        PatientAppAPIs.coachPsychologicalTestControllerPatientAPI = psych;

        CoachExerciseControllerPatientAPI ex = mock(CoachExerciseControllerPatientAPI.class);
        when(ex.getAllExercises(anyString())).thenThrow(new RuntimeException("x"));
        PatientAppAPIs.coachExerciseControllerPatientAPI = ex;

        CoachChatbotControllerPatientAPI chat = mock(CoachChatbotControllerPatientAPI.class);
        when(chat.getConversationSummary(any(GetConversationSummaryInputDTOPatientAPI.class), anyString())).thenThrow(new RuntimeException("x"));
        PatientAppAPIs.coachChatbotControllerPatientAPI = chat;

        CoachJournalEntryControllerPatientAPI journal = mock(CoachJournalEntryControllerPatientAPI.class);
        when(journal.listAll2(anyString())).thenThrow(new RuntimeException("x"));
        PatientAppAPIs.coachJournalEntryControllerPatientAPI = journal;

        CoachLogControllerPatientAPI log = mock(CoachLogControllerPatientAPI.class);
        for (LogTypes t : LogTypes.values()) {
            when(log.listAll1(anyString(), eq(t.name()))).thenReturn(Flux.fromIterable(Collections.emptyList()));
        }
        PatientAppAPIs.coachLogControllerPatientAPI = log;

        String ctx = new PatientAppContextService().buildContext("pid2");
        assertTrue(ctx.contains("## Client-App Context"));
        assertFalse(ctx.contains("Latest GAD-7"));
        assertFalse(ctx.contains("Exercise Completions (last 30 days)"));
        assertFalse(ctx.contains("Chatbot Summary (last 30 days)"));
        assertFalse(ctx.contains("Recent Journal Entries"));
        assertFalse(ctx.contains("Log Counts per Week"));
    }
}
