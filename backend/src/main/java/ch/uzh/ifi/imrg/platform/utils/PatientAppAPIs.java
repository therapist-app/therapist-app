package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PatientAppAPIs {

  public static PatientControllerPatientAPI patientControllerPatientAPI;

  @Autowired
  public PatientAppAPIs(@Value("${PATIENT_APP_URL}") String PATIENT_APP_URL) {
    System.out.println("PATIENT_APP_URL: " + PATIENT_APP_URL);
    patientControllerPatientAPI = new PatientControllerPatientAPI();
    patientControllerPatientAPI.getApiClient().setBasePath(PATIENT_APP_URL);
  }
}
