package ch.uzh.ifi.imrg.platform.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariables {

  static String JWT_SECRET_KEY;
  static String LOCAL_LLM_URL;
  static String LOCAL_LLM_API_KEY;
  static String CHAT_GPT_API_KEY;

  @Autowired
  public EnvironmentVariables(
      @Value("${JWT_SECRET_KEY}") String JWT_SECRET_KEY,
      @Value("${LOCAL_LLM_URL}") String LOCAL_LLM_URL,
      @Value("${LOCAL_LLM_API_KEY}") String LOCAL_LLM_API_KEY,
      @Value("${CHAT_GPT_API_KEY}") String CHAT_GPT_API_KEY) {
    EnvironmentVariables.JWT_SECRET_KEY = JWT_SECRET_KEY;
    EnvironmentVariables.LOCAL_LLM_URL = LOCAL_LLM_URL;
    EnvironmentVariables.LOCAL_LLM_API_KEY = LOCAL_LLM_API_KEY;
    EnvironmentVariables.CHAT_GPT_API_KEY = CHAT_GPT_API_KEY;
  }
}
