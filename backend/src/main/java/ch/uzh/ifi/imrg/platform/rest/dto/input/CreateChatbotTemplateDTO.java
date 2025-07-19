package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class CreateChatbotTemplateDTO {
  private String chatbotName;
  private String chatbotIcon;
  private String chatbotRole;
  private String chatbotTone;
  private String welcomeMessage;
  private Boolean isActive;
}
