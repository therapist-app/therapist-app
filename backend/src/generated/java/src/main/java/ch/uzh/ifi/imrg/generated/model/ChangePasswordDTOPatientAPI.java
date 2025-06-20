/*
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
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
 * ChangePasswordDTOPatientAPI
 */
@JsonPropertyOrder({
  ChangePasswordDTOPatientAPI.JSON_PROPERTY_OLD_PASSWORD,
  ChangePasswordDTOPatientAPI.JSON_PROPERTY_NEW_PASSWORD,
  ChangePasswordDTOPatientAPI.JSON_PROPERTY_CONFIRM_PASSWORD
})
@JsonTypeName("ChangePasswordDTO")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class ChangePasswordDTOPatientAPI {
  public static final String JSON_PROPERTY_OLD_PASSWORD = "oldPassword";
  private String oldPassword;

  public static final String JSON_PROPERTY_NEW_PASSWORD = "newPassword";
  private String newPassword;

  public static final String JSON_PROPERTY_CONFIRM_PASSWORD = "confirmPassword";
  private String confirmPassword;

  public ChangePasswordDTOPatientAPI() {
  }

  public ChangePasswordDTOPatientAPI oldPassword(String oldPassword) {
    
    this.oldPassword = oldPassword;
    return this;
  }

   /**
   * Get oldPassword
   * @return oldPassword
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_OLD_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getOldPassword() {
    return oldPassword;
  }


  @JsonProperty(JSON_PROPERTY_OLD_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }


  public ChangePasswordDTOPatientAPI newPassword(String newPassword) {
    
    this.newPassword = newPassword;
    return this;
  }

   /**
   * Get newPassword
   * @return newPassword
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NEW_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getNewPassword() {
    return newPassword;
  }


  @JsonProperty(JSON_PROPERTY_NEW_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }


  public ChangePasswordDTOPatientAPI confirmPassword(String confirmPassword) {
    
    this.confirmPassword = confirmPassword;
    return this;
  }

   /**
   * Get confirmPassword
   * @return confirmPassword
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CONFIRM_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getConfirmPassword() {
    return confirmPassword;
  }


  @JsonProperty(JSON_PROPERTY_CONFIRM_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChangePasswordDTOPatientAPI changePasswordDTO = (ChangePasswordDTOPatientAPI) o;
    return Objects.equals(this.oldPassword, changePasswordDTO.oldPassword) &&
        Objects.equals(this.newPassword, changePasswordDTO.newPassword) &&
        Objects.equals(this.confirmPassword, changePasswordDTO.confirmPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oldPassword, newPassword, confirmPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChangePasswordDTOPatientAPI {\n");
    sb.append("    oldPassword: ").append(toIndentedString(oldPassword)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
    sb.append("    confirmPassword: ").append(toIndentedString(confirmPassword)).append("\n");
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

