package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import java.util.List;
import java.util.stream.Collectors;

public class LLMAzureOpenai extends LLM {

  private final OpenAIClient client;

  public LLMAzureOpenai() {
    this.client =
        new OpenAIClientBuilder()
            .endpoint(EnvironmentVariables.AZURE_OPENAI_ENDPOINT)
            .credential(new AzureKeyCredential(EnvironmentVariables.AZURE_OPENAI_API_KEY))
            .buildClient();
  }

  @Override
  protected String getRawLLMResponse(List<ChatMessageDTO> messages, Language language) {
    List<ChatRequestMessage> sdkMessages =
        messages.stream().map(this::mapToSdkMessage).collect(Collectors.toList());

    ChatCompletionsOptions options = new ChatCompletionsOptions(sdkMessages).setTemperature(0.1);

    ChatCompletions chatCompletions =
        client.getChatCompletions(EnvironmentVariables.AZURE_OPENAI_DEPLOYMENT_NAME, options);

    if (chatCompletions != null && !chatCompletions.getChoices().isEmpty()) {
      return chatCompletions.getChoices().get(0).getMessage().getContent();
    }

    throw new RuntimeException("Did not receive a valid message from the Azure OpenAI API.");
  }

  private ChatRequestMessage mapToSdkMessage(ChatMessageDTO dto) {
    String content = dto.getContent();
    switch (dto.getChatRole()) {
      case USER:
        return new ChatRequestUserMessage(content);
      case ASSISTANT:
        return new ChatRequestAssistantMessage(content);
      case SYSTEM:
        return new ChatRequestSystemMessage(content);
      default:
        throw new IllegalArgumentException("Invalid chat role: " + dto.getChatRole());
    }
  }
}
