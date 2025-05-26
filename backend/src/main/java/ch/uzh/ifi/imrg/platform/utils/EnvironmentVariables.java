package ch.uzh.ifi.imrg.platform.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariables {

  public static String JWT_SECRET_KEY;
  public static String LOCAL_LLM_MODEL;
  public static String LOCAL_LLM_URL;
  public static String LOCAL_LLM_API_KEY;
  public static String PATIENT_APP_URL;

  @Autowired
  public EnvironmentVariables(
      @Value("${JWT_SECRET_KEY}") String JWT_SECRET_KEY,
      @Value("${LOCAL_LLM_MODEL}") String LOCAL_LLM_MODEL,
      @Value("${LOCAL_LLM_URL}") String LOCAL_LLM_URL,
      @Value("${LOCAL_LLM_API_KEY}") String LOCAL_LLM_API_KEY,
      @Value("${PATIENT_APP_URL}") String PATIENT_APP_URL) {
    EnvironmentVariables.JWT_SECRET_KEY = JWT_SECRET_KEY;
    EnvironmentVariables.LOCAL_LLM_MODEL = LOCAL_LLM_MODEL;
    EnvironmentVariables.LOCAL_LLM_URL = LOCAL_LLM_URL;
    EnvironmentVariables.LOCAL_LLM_API_KEY = LOCAL_LLM_API_KEY;
    EnvironmentVariables.PATIENT_APP_URL = PATIENT_APP_URL;
  }
}
