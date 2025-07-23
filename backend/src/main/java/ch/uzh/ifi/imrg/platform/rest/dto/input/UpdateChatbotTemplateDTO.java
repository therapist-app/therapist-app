package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class UpdateChatbotTemplateDTO {
  private String chatbotName;
  private String chatbotRole;
  private String chatbotTone;
  private String welcomeMessage;
  private Boolean isActive;
}
