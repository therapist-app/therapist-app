// src/main/java/ch/uzh/ifi/imrg/platform/LLM/LLMFactory.java

package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LLMFactory {

  private static final Logger logger = LoggerFactory.getLogger(LLMFactory.class);

  private static volatile LLM instance;

  private LLMFactory() {}

  public static LLM getInstance() {
    if (instance == null) {
      synchronized (LLMFactory.class) {
        if (instance == null) {
          String provider = EnvironmentVariables.LLM_PROVIDER;
          logger.info("Initializing LLM provider: {}", provider);

          switch (provider.toUpperCase()) {
            case "AZURE":
              instance = new LLMAzureOpenai();
              break;
            case "UZH":
              instance = new LLMUZH();
              break;
            default:
              logger.error("Invalid LLM_PROVIDER: '{}'. Defaulting to UZH.", provider);
              instance = new LLMUZH();
          }
        }
      }
    }
    return instance;
  }
}
