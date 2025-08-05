package ch.uzh.ifi.imrg.platform.enums;

public enum LLMModel {
  LOCAL_UZH(2_000),

  AZURE_OPENAI(150_000);

  private final int maxTokens;

  LLMModel(int maxTokens) {
    this.maxTokens = maxTokens;
  }

  public int getMaxTokens() {
    return maxTokens;
  }
}
