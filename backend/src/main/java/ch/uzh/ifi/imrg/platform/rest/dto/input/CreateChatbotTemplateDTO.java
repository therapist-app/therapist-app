package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatbotTemplateDTO {
  private String chatbotName;
  private String description;
  private String chatbotModel;
  private String chatbotIcon;
  private String chatbotLanguage;
  private String chatbotRole;
  private String chatbotTone;
  private String welcomeMessage;
  private String workspaceId;
}
