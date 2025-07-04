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
 * MessageOutputDTOPatientAPI
 */
@JsonPropertyOrder({
  MessageOutputDTOPatientAPI.JSON_PROPERTY_TIMESTAMP,
  MessageOutputDTOPatientAPI.JSON_PROPERTY_ID,
  MessageOutputDTOPatientAPI.JSON_PROPERTY_CONVERSATION_ID,
  MessageOutputDTOPatientAPI.JSON_PROPERTY_RESPONSE_MESSAGE,
  MessageOutputDTOPatientAPI.JSON_PROPERTY_REQUEST_MESSAGE
})
@JsonTypeName("MessageOutputDTO")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class MessageOutputDTOPatientAPI {
  public static final String JSON_PROPERTY_TIMESTAMP = "timestamp";
  private String timestamp;

  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public static final String JSON_PROPERTY_CONVERSATION_ID = "conversationId";
  private String conversationId;

  public static final String JSON_PROPERTY_RESPONSE_MESSAGE = "responseMessage";
  private String responseMessage;

  public static final String JSON_PROPERTY_REQUEST_MESSAGE = "requestMessage";
  private String requestMessage;

  public MessageOutputDTOPatientAPI() {
  }

  public MessageOutputDTOPatientAPI timestamp(String timestamp) {
    
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Get timestamp
   * @return timestamp
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTimestamp() {
    return timestamp;
  }


  @JsonProperty(JSON_PROPERTY_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }


  public MessageOutputDTOPatientAPI id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getId() {
    return id;
  }


  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(String id) {
    this.id = id;
  }


  public MessageOutputDTOPatientAPI conversationId(String conversationId) {
    
    this.conversationId = conversationId;
    return this;
  }

   /**
   * Get conversationId
   * @return conversationId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONVERSATION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getConversationId() {
    return conversationId;
  }


  @JsonProperty(JSON_PROPERTY_CONVERSATION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }


  public MessageOutputDTOPatientAPI responseMessage(String responseMessage) {
    
    this.responseMessage = responseMessage;
    return this;
  }

   /**
   * Get responseMessage
   * @return responseMessage
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_RESPONSE_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getResponseMessage() {
    return responseMessage;
  }


  @JsonProperty(JSON_PROPERTY_RESPONSE_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
  }


  public MessageOutputDTOPatientAPI requestMessage(String requestMessage) {
    
    this.requestMessage = requestMessage;
    return this;
  }

   /**
   * Get requestMessage
   * @return requestMessage
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_REQUEST_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getRequestMessage() {
    return requestMessage;
  }


  @JsonProperty(JSON_PROPERTY_REQUEST_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRequestMessage(String requestMessage) {
    this.requestMessage = requestMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageOutputDTOPatientAPI messageOutputDTO = (MessageOutputDTOPatientAPI) o;
    return Objects.equals(this.timestamp, messageOutputDTO.timestamp) &&
        Objects.equals(this.id, messageOutputDTO.id) &&
        Objects.equals(this.conversationId, messageOutputDTO.conversationId) &&
        Objects.equals(this.responseMessage, messageOutputDTO.responseMessage) &&
        Objects.equals(this.requestMessage, messageOutputDTO.requestMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, id, conversationId, responseMessage, requestMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageOutputDTOPatientAPI {\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    conversationId: ").append(toIndentedString(conversationId)).append("\n");
    sb.append("    responseMessage: ").append(toIndentedString(responseMessage)).append("\n");
    sb.append("    requestMessage: ").append(toIndentedString(requestMessage)).append("\n");
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

