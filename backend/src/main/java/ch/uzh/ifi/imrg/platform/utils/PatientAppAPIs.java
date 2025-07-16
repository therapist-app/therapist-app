package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.generated.api.CoachChatbotControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachDocumentControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachExerciseControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachJournalEntryControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachMeetingControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachPatientControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.api.CoachPsychologicalTestControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PatientAppAPIs {

  public static CoachChatbotControllerPatientAPI coachChatbotControllerPatientAPI;
  public static CoachDocumentControllerPatientAPI coachDocumentControllerPatientAPI;
  public static CoachExerciseControllerPatientAPI coachExerciseControllerPatientAPI;
  public static CoachJournalEntryControllerPatientAPI coachJournalEntryControllerPatientAPI;
  public static CoachMeetingControllerPatientAPI coachMeetingControllerPatientAPI;
  public static CoachPatientControllerPatientAPI coachPatientControllerPatientAPI;
  public static CoachPsychologicalTestControllerPatientAPI coachPsychologicalTestControllerPatientAPI;
  public static String COACH_ACCESS_KEY;
  public static String PATIENT_APP_URL;

  @Autowired
  public PatientAppAPIs(
      @Value("${PATIENT_APP_URL}") String PATIENT_APP_URL,
      @Value("${COACH_ACCESS_KEY}") String COACH_ACCESS_KEY) {
    PatientAppAPIs.COACH_ACCESS_KEY = COACH_ACCESS_KEY;
    PatientAppAPIs.PATIENT_APP_URL = PATIENT_APP_URL;

    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(PATIENT_APP_URL);
    apiClient.setApiKey(COACH_ACCESS_KEY);

    coachChatbotControllerPatientAPI = new CoachChatbotControllerPatientAPI(apiClient);
    coachDocumentControllerPatientAPI = new CoachDocumentControllerPatientAPI(apiClient);
    coachExerciseControllerPatientAPI = new CoachExerciseControllerPatientAPI(apiClient);
    coachJournalEntryControllerPatientAPI = new CoachJournalEntryControllerPatientAPI(apiClient);
    coachMeetingControllerPatientAPI = new CoachMeetingControllerPatientAPI(apiClient);
    coachPatientControllerPatientAPI = new CoachPatientControllerPatientAPI(apiClient);
    coachPsychologicalTestControllerPatientAPI = new CoachPsychologicalTestControllerPatientAPI(apiClient);
  }
}
