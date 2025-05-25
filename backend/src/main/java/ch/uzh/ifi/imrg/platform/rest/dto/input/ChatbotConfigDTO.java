package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class ChatbotConfigDTO {
  private String chatbotRole;
  private String chatbotTone;
  private String chatbotLanguage;
  private String chatbotVoice;
  private String chatbotGender;
  private String preConfiguredExercise;
  private String additionalExercise;
  private String welcomeMessage;
}
