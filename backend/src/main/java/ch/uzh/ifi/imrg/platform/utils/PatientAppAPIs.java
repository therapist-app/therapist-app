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
  public static CoachPsychologicalTestControllerPatientAPI
      coachPsychologicalTestControllerPatientAPI;

  @Autowired
  public PatientAppAPIs(@Value("${PATIENT_APP_URL}") String PATIENT_APP_URL) {

    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(PATIENT_APP_URL);

    coachChatbotControllerPatientAPI = new CoachChatbotControllerPatientAPI(apiClient);
    coachDocumentControllerPatientAPI = new CoachDocumentControllerPatientAPI(apiClient);
    coachExerciseControllerPatientAPI = new CoachExerciseControllerPatientAPI(apiClient);
    coachJournalEntryControllerPatientAPI = new CoachJournalEntryControllerPatientAPI(apiClient);
    coachMeetingControllerPatientAPI = new CoachMeetingControllerPatientAPI(apiClient);
    coachPatientControllerPatientAPI = new CoachPatientControllerPatientAPI(apiClient);
    coachPsychologicalTestControllerPatientAPI =
        new CoachPsychologicalTestControllerPatientAPI(apiClient);
  }
}
