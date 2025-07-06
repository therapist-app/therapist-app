package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.Instant;
import lombok.Data;

@Data
public class ChatbotTemplateOutputDTO {
  private String id;
  private String chatbotName;
  private String description;
  private String chatbotModel;
  private String chatbotIcon;
  private String chatbotLanguage;
  private String chatbotRole;
  private String chatbotTone;
  private String welcomeMessage;
  private String chatbotVoice;
  private String chatbotGender;
  private String preConfiguredExercise;
  private String additionalExercise;
  private String animation;
  private String chatbotInputPlaceholder;
  private Instant createdAt;
  private Instant updatedAt;
}
