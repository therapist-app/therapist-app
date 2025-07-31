package ch.uzh.ifi.imrg.platform.health;

import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("patientApp")
public class PatientAppHealthIndicator extends AbstractUrlHealthIndicator {

  public PatientAppHealthIndicator(RestTemplate restTemplate) {
    super(restTemplate);
  }

  @Override
  protected String getUrl() {
    return EnvironmentVariables.PATIENT_APP_URL;
  }
}
