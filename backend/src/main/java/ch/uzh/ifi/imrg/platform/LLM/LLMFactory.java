package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LLMFactory {

  private static final Logger logger = LoggerFactory.getLogger(LLMFactory.class);

  private static volatile LLM instance;

  private LLMFactory() {}

  public static LLM getInstance(LLMModel llmModel) {
    if (instance == null) {
      synchronized (LLMFactory.class) {
        if (instance == null) {
          logger.info("Initializing LLM model: {}", llmModel);

          switch (llmModel) {
            case LLMModel.AZURE_OPENAI:
              instance = new LLMAzureOpenai();
              break;
            case LLMModel.LOCAL_UZH:
              instance = new LLMUZH();
              break;
            default:
              logger.error("Invalid LLM Model: '{}'. Defaulting to LOCAL_UZH.", llmModel);
              instance = new LLMUZH();
          }
        }
      }
    }
    return instance;
  }
}
