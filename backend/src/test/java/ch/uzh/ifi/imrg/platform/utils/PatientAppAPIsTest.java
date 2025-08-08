package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.generated.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientAppAPIsTest {

    String prevUrl = PatientAppAPIs.PATIENT_APP_URL;
    String prevKey = PatientAppAPIs.COACH_ACCESS_KEY;

    @AfterEach
    void restore() {
        PatientAppAPIs.PATIENT_APP_URL = prevUrl;
        PatientAppAPIs.COACH_ACCESS_KEY = prevKey;
    }

    @Test
    void constructsAndSetsStatics() {
        String url = prevUrl != null ? prevUrl : "http://localhost";
        String key = prevKey != null ? prevKey : "key";
        new PatientAppAPIs(url, key);
        assertEquals(url, PatientAppAPIs.PATIENT_APP_URL);
        assertEquals(key, PatientAppAPIs.COACH_ACCESS_KEY);
        assertNotNull(PatientAppAPIs.coachChatbotControllerPatientAPI);
        assertNotNull(PatientAppAPIs.coachDocumentControllerPatientAPI);
        assertNotNull(PatientAppAPIs.coachExerciseControllerPatientAPI);
        assertNotNull(PatientAppAPIs.coachJournalEntryControllerPatientAPI);
        assertNotNull(PatientAppAPIs.coachMeetingControllerPatientAPI);
        assertNotNull(PatientAppAPIs.coachPatientControllerPatientAPI);
        assertNotNull(PatientAppAPIs.coachPsychologicalTestControllerPatientAPI);
        assertNotNull(PatientAppAPIs.coachLogControllerPatientAPI);
        assertTrue(PatientAppAPIs.coachChatbotControllerPatientAPI instanceof CoachChatbotControllerPatientAPI);
        assertTrue(PatientAppAPIs.coachDocumentControllerPatientAPI instanceof CoachDocumentControllerPatientAPI);
        assertTrue(PatientAppAPIs.coachExerciseControllerPatientAPI instanceof CoachExerciseControllerPatientAPI);
        assertTrue(PatientAppAPIs.coachJournalEntryControllerPatientAPI instanceof CoachJournalEntryControllerPatientAPI);
        assertTrue(PatientAppAPIs.coachMeetingControllerPatientAPI instanceof CoachMeetingControllerPatientAPI);
        assertTrue(PatientAppAPIs.coachPatientControllerPatientAPI instanceof CoachPatientControllerPatientAPI);
        assertTrue(PatientAppAPIs.coachPsychologicalTestControllerPatientAPI instanceof CoachPsychologicalTestControllerPatientAPI);
        assertTrue(PatientAppAPIs.coachLogControllerPatientAPI instanceof CoachLogControllerPatientAPI);
    }
}
