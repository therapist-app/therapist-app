package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatCompletionResponseDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatMessageService {

  public ChatMessageService() {}

  public ChatCompletionResponseDTO chat(ChatCompletionWithConfigRequestDTO req) {

    String systemPrompt = buildSystemPrompt(req.getConfig());

    List<ChatMessageDTO> msgs = new ArrayList<>();
    msgs.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    if (req.getHistory() != null && !req.getHistory().isEmpty()) msgs.addAll(req.getHistory());
    msgs.add(new ChatMessageDTO(ChatRole.USER, req.getMessage()));

    String responseMessage = LLMUZH.callLLM(msgs);
    return new ChatCompletionResponseDTO(responseMessage);
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
}
