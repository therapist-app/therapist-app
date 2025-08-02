package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LLMFactory {

  private static final Logger logger = LoggerFactory.getLogger(LLMFactory.class);

  private LLMFactory() {}

  public static LLM createInstance(LLMModel llmModel) {
    logger.info("Creating a new LLM instance for model: {}", llmModel);

    switch (llmModel) {
      case AZURE_OPENAI:
        return new LLMAzureOpenai();
      case LOCAL_UZH:
        return new LLMUZH();
      default:
        logger.error("Invalid LLM Model: '{}'. Defaulting to LOCAL_UZH.", llmModel);
        return new LLMUZH();
    }
  }
}
