package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatCompletionResponseDTO;
import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class ChatMessageService {

  private final RestTemplate restTemplate;

  public ChatMessageService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ChatCompletionResponseDTO chat(ChatCompletionWithConfigRequestDTO req) {

    String systemPrompt = buildSystemPrompt(req.getConfig());

    List<ChatMessageDTO> msgs = new ArrayList<>();
    msgs.add(new ChatMessageDTO("system", systemPrompt));
    if (req.getHistory() != null && !req.getHistory().isEmpty()) msgs.addAll(req.getHistory());
    msgs.add(new ChatMessageDTO("user", req.getMessage()));

    return callRemote(modelRequest(msgs));
  }

  private ChatCompletionResponseDTO callRemote(RemoteRequest payload) {

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(EnvironmentVariables.LOCAL_LLM_API_KEY);
    headers.setContentType(MediaType.APPLICATION_JSON);

    ResponseEntity<RemoteResponse> response =
        restTemplate.exchange(
            EnvironmentVariables.LOCAL_LLM_URL,
            HttpMethod.POST,
            new HttpEntity<>(payload, headers),
            RemoteResponse.class);

    String assistantReply = response.getBody().choices[0].message.content;
    return new ChatCompletionResponseDTO(assistantReply);
  }

  private RemoteRequest modelRequest(List<ChatMessageDTO> messages) {
    return new RemoteRequest(EnvironmentVariables.LOCAL_LLM_MODEL, messages);
  }

  private String buildSystemPrompt(ChatbotConfigDTO c) {
    StringBuilder sb = new StringBuilder("You are a helpful assistant.");
    if (nonEmpty(c.getChatbotRole()))
      sb.append("\nYour role is **").append(c.getChatbotRole()).append("**.");
    if (nonEmpty(c.getChatbotTone()))
      sb.append("\nSpeak with a **").append(c.getChatbotTone()).append("** tone.");
    if (nonEmpty(c.getChatbotLanguage()))
      sb.append("\nReply in **").append(c.getChatbotLanguage()).append("**.");
    if (nonEmpty(c.getChatbotVoice()) && !"None".equalsIgnoreCase(c.getChatbotVoice()))
      sb.append("\nWhen TTS is requested, use a **")
          .append(c.getChatbotVoice())
          .append("** voice.");
    if (nonEmpty(c.getChatbotGender()))
      sb.append("\nYour persona is **").append(c.getChatbotGender()).append("**.");
    if (nonEmpty(c.getPreConfiguredExercise()))
      sb.append("\nGuide the user through the pre-configured exercise: “")
          .append(c.getPreConfiguredExercise())
          .append("”.");
    if (nonEmpty(c.getAdditionalExercise()))
      sb.append("\nOptionally offer the additional exercise: “")
          .append(c.getAdditionalExercise())
          .append("”.");
    if (nonEmpty(c.getWelcomeMessage()))
      sb.append("\nYour default welcome message is: “").append(c.getWelcomeMessage()).append("”.");
    return sb.toString();
  }

  private boolean nonEmpty(String s) {
    return s != null && !s.isBlank();
  }

  private record RemoteRequest(
      String model, @JsonProperty("messages") List<ChatMessageDTO> messages) {}

  private record RemoteResponse(RemoteChoice[] choices) {
    private record RemoteChoice(RemoteMessage message) {}

    private record RemoteMessage(String content) {}
  }
}
