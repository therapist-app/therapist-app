package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateChatbotTemplateDTO {
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
  private String workspaceId;
}
