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
  public static String AZURE_OPENAI_ENDPOINT;
  public static String AZURE_OPENAI_API_KEY;
  public static String AZURE_OPENAI_DEPLOYMENT_NAME;
  public static Integer LLM_MAX_CHARACTERS;
  public static Integer MAX_CHARACTERS_PER_PDF;
  public static Boolean IS_PRODUCTION;

  @Autowired
  public EnvironmentVariables(
      @Value("${PATIENT_APP_URL}") String PATIENT_APP_URL,
      @Value("${JWT_SECRET_KEY}") String JWT_SECRET_KEY,
      @Value("${LOCAL_LLM_MODEL:}") String LOCAL_LLM_MODEL,
      @Value("${LOCAL_LLM_URL:}") String LOCAL_LLM_URL,
      @Value("${LOCAL_LLM_API_KEY:}") String LOCAL_LLM_API_KEY,
      @Value("${APP_ENVIRONMENT:dev}") String APP_ENVIRONMENT,
      @Value("${AZURE_OPENAI_ENDPOINT:}") String AZURE_OPENAI_ENDPOINT,
      @Value("${AZURE_OPENAI_API_KEY:}") String AZURE_OPENAI_API_KEY,
      @Value("${AZURE_OPENAI_DEPLOYMENT_NAME:}") String AZURE_OPENAI_DEPLOYMENT_NAME,
      @Value("${LLM_MAX_CHARACTERS:150000}") Integer LLM_MAX_CHARACTERS,
      @Value("${MAX_CHARACTERS_PER_PDF:20000}") Integer MAX_CHARACTERS_PER_PDF) {
    EnvironmentVariables.JWT_SECRET_KEY = JWT_SECRET_KEY;
    EnvironmentVariables.LOCAL_LLM_MODEL = LOCAL_LLM_MODEL;
    EnvironmentVariables.LOCAL_LLM_URL = LOCAL_LLM_URL;
    EnvironmentVariables.LOCAL_LLM_API_KEY = LOCAL_LLM_API_KEY;
    EnvironmentVariables.PATIENT_APP_URL = PATIENT_APP_URL;
    EnvironmentVariables.AZURE_OPENAI_ENDPOINT = AZURE_OPENAI_ENDPOINT;
    EnvironmentVariables.AZURE_OPENAI_API_KEY = AZURE_OPENAI_API_KEY;
    EnvironmentVariables.AZURE_OPENAI_DEPLOYMENT_NAME = AZURE_OPENAI_DEPLOYMENT_NAME;
    EnvironmentVariables.LLM_MAX_CHARACTERS = LLM_MAX_CHARACTERS;
    EnvironmentVariables.MAX_CHARACTERS_PER_PDF = MAX_CHARACTERS_PER_PDF;
    EnvironmentVariables.IS_PRODUCTION = APP_ENVIRONMENT.equalsIgnoreCase("prod");

    System.out.println("IS_PRODUCTION: " + EnvironmentVariables.IS_PRODUCTION);
  }
}
