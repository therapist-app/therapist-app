/*
 * 
 * 
 *
 * 
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package ch.uzh.ifi.imrg.generated.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * CreateChatbotDTOPatientAPI
 */
@JsonPropertyOrder({
  CreateChatbotDTOPatientAPI.JSON_PROPERTY_CHATBOT_ROLE,
  CreateChatbotDTOPatientAPI.JSON_PROPERTY_CHATBOT_TONE,
  CreateChatbotDTOPatientAPI.JSON_PROPERTY_WELCOME_MESSAGE
})
@JsonTypeName("CreateChatbotDTO")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class CreateChatbotDTOPatientAPI {
  public static final String JSON_PROPERTY_CHATBOT_ROLE = "chatbotRole";
  private String chatbotRole;

  public static final String JSON_PROPERTY_CHATBOT_TONE = "chatbotTone";
  private String chatbotTone;

  public static final String JSON_PROPERTY_WELCOME_MESSAGE = "welcomeMessage";
  private String welcomeMessage;

  public CreateChatbotDTOPatientAPI() {
  }

  public CreateChatbotDTOPatientAPI chatbotRole(String chatbotRole) {
    
    this.chatbotRole = chatbotRole;
    return this;
  }

   /**
   * Get chatbotRole
   * @return chatbotRole
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CHATBOT_ROLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getChatbotRole() {
    return chatbotRole;
  }


  @JsonProperty(JSON_PROPERTY_CHATBOT_ROLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setChatbotRole(String chatbotRole) {
    this.chatbotRole = chatbotRole;
  }


  public CreateChatbotDTOPatientAPI chatbotTone(String chatbotTone) {
    
    this.chatbotTone = chatbotTone;
    return this;
  }

   /**
   * Get chatbotTone
   * @return chatbotTone
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CHATBOT_TONE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getChatbotTone() {
    return chatbotTone;
  }


  @JsonProperty(JSON_PROPERTY_CHATBOT_TONE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setChatbotTone(String chatbotTone) {
    this.chatbotTone = chatbotTone;
  }


  public CreateChatbotDTOPatientAPI welcomeMessage(String welcomeMessage) {
    
    this.welcomeMessage = welcomeMessage;
    return this;
  }

   /**
   * Get welcomeMessage
   * @return welcomeMessage
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WELCOME_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getWelcomeMessage() {
    return welcomeMessage;
  }


  @JsonProperty(JSON_PROPERTY_WELCOME_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWelcomeMessage(String welcomeMessage) {
    this.welcomeMessage = welcomeMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateChatbotDTOPatientAPI createChatbotDTO = (CreateChatbotDTOPatientAPI) o;
    return Objects.equals(this.chatbotRole, createChatbotDTO.chatbotRole) &&
        Objects.equals(this.chatbotTone, createChatbotDTO.chatbotTone) &&
        Objects.equals(this.welcomeMessage, createChatbotDTO.welcomeMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chatbotRole, chatbotTone, welcomeMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateChatbotDTOPatientAPI {\n");
    sb.append("    chatbotRole: ").append(toIndentedString(chatbotRole)).append("\n");
    sb.append("    chatbotTone: ").append(toIndentedString(chatbotTone)).append("\n");
    sb.append("    welcomeMessage: ").append(toIndentedString(welcomeMessage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

