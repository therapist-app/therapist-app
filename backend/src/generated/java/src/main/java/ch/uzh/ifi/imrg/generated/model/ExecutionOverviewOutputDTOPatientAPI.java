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
 * ExecutionOverviewOutputDTOPatientAPI
 */
@JsonPropertyOrder({
  ExecutionOverviewOutputDTOPatientAPI.JSON_PROPERTY_EXECUTION_TITLE,
  ExecutionOverviewOutputDTOPatientAPI.JSON_PROPERTY_EXERCISE_EXECUTION_ID
})
@JsonTypeName("ExecutionOverviewOutputDTO")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class ExecutionOverviewOutputDTOPatientAPI {
  public static final String JSON_PROPERTY_EXECUTION_TITLE = "executionTitle";
  private String executionTitle;

  public static final String JSON_PROPERTY_EXERCISE_EXECUTION_ID = "exerciseExecutionId";
  private String exerciseExecutionId;

  public ExecutionOverviewOutputDTOPatientAPI() {
  }

  public ExecutionOverviewOutputDTOPatientAPI executionTitle(String executionTitle) {
    
    this.executionTitle = executionTitle;
    return this;
  }

   /**
   * Get executionTitle
   * @return executionTitle
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_EXECUTION_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getExecutionTitle() {
    return executionTitle;
  }


  @JsonProperty(JSON_PROPERTY_EXECUTION_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setExecutionTitle(String executionTitle) {
    this.executionTitle = executionTitle;
  }


  public ExecutionOverviewOutputDTOPatientAPI exerciseExecutionId(String exerciseExecutionId) {
    
    this.exerciseExecutionId = exerciseExecutionId;
    return this;
  }

   /**
   * Get exerciseExecutionId
   * @return exerciseExecutionId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_EXERCISE_EXECUTION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getExerciseExecutionId() {
    return exerciseExecutionId;
  }


  @JsonProperty(JSON_PROPERTY_EXERCISE_EXECUTION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setExerciseExecutionId(String exerciseExecutionId) {
    this.exerciseExecutionId = exerciseExecutionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExecutionOverviewOutputDTOPatientAPI executionOverviewOutputDTO = (ExecutionOverviewOutputDTOPatientAPI) o;
    return Objects.equals(this.executionTitle, executionOverviewOutputDTO.executionTitle) &&
        Objects.equals(this.exerciseExecutionId, executionOverviewOutputDTO.exerciseExecutionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(executionTitle, exerciseExecutionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExecutionOverviewOutputDTOPatientAPI {\n");
    sb.append("    executionTitle: ").append(toIndentedString(executionTitle)).append("\n");
    sb.append("    exerciseExecutionId: ").append(toIndentedString(exerciseExecutionId)).append("\n");
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

