package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientAppAPIs {

  public static PatientControllerPatientAPI patientControllerPatientAPI;

  @Autowired
  public PatientAppAPIs() {
    String PATIENT_APP_URL = EnvironmentVariables.PATIENT_APP_URL;
    patientControllerPatientAPI =
        new PatientControllerPatientAPI().getApiClient().setBasePath(PATIENT_APP_URL);
  }
}
