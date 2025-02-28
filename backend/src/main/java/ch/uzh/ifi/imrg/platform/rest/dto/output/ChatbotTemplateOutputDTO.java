package ch.uzh.ifi.imrg.platform.rest.dto.output;

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
  private String workspaceId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getChatbotName() {
    return chatbotName;
  }

  public void setChatbotName(String chatbotName) {
    this.chatbotName = chatbotName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getChatbotModel() {
    return chatbotModel;
  }

  public void setChatbotModel(String chatbotModel) {
    this.chatbotModel = chatbotModel;
  }

  public String getChatbotIcon() {
    return chatbotIcon;
  }

  public void setChatbotIcon(String chatbotIcon) {
    this.chatbotIcon = chatbotIcon;
  }

  public String getChatbotLanguage() {
    return chatbotLanguage;
  }

  public void setChatbotLanguage(String chatbotLanguage) {
    this.chatbotLanguage = chatbotLanguage;
  }

  public String getChatbotRole() {
    return chatbotRole;
  }

  public void setChatbotRole(String chatbotRole) {
    this.chatbotRole = chatbotRole;
  }

  public String getChatbotTone() {
    return chatbotTone;
  }

  public void setChatbotTone(String chatbotTone) {
    this.chatbotTone = chatbotTone;
  }

  public String getWelcomeMessage() {
    return welcomeMessage;
  }

  public void setWelcomeMessage(String welcomeMessage) {
    this.welcomeMessage = welcomeMessage;
  }

  public String getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(String workspaceId) {
    this.workspaceId = workspaceId;
  }
}
