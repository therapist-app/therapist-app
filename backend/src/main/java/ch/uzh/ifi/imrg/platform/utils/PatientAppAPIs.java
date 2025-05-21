package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientAppAPIs {

  public static PatientControllerPatientAPI patientControllerPatientAPI;

  @Autowired
  public PatientAppAPIs() {
    String PATIENT_APP_URL = EnvironmentVariables.PATIENT_APP_URL;
    System.out.println("PATIENT_APP_URL: " + PATIENT_APP_URL);
    patientControllerPatientAPI = new PatientControllerPatientAPI();
    ApiClient apiClient = patientControllerPatientAPI.getApiClient();
    apiClient.setBasePath("https://backend-patient-app-main.jonas-blum.ch");
    patientControllerPatientAPI.setApiClient(apiClient);
  }
}
