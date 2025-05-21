package ch.uzh.ifi.imrg.platform.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.uzh.ifi.imrg.generated.api.PatientControllerPatientAPI;

@Component
public class PatientAppAPIs {

    public static PatientControllerPatientAPI patientControllerPatientAPI;

    @Autowired
    public PatientAppAPIs() {
        String PATIENT_APP_URL = EnvironmentVariables.PATIENT_APP_URL;
        patientControllerPatientAPI = new PatientControllerPatientAPI().getApiClient().setBasePath(PATIENT_APP_URL);
    }
}
