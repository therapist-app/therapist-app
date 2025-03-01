package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.util.List;

public class TherapistOutputDTO {
  private String id;
  private String email;
  private String workspaceId;
  private List<ChatbotTemplateOutputDTO> chatbotTemplatesOutputDTO;
  private List<PatientOutputDTO> patientsOutputDTO;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(String workspaceId) {
    this.workspaceId = workspaceId;
  }

  public List<ChatbotTemplateOutputDTO> getChatbotTemplatesOutputDTO() {
    return chatbotTemplatesOutputDTO;
  }

  public void setChatbotTemplatesOutputDTO(List<ChatbotTemplateOutputDTO> chatbotTemplatesOutputDTO) {
    this.chatbotTemplatesOutputDTO = chatbotTemplatesOutputDTO;
  }

  public List<PatientOutputDTO> getPatientsOutputDTO() {
    return patientsOutputDTO;
  }

  public void setPatientsOutputDTO(List<PatientOutputDTO> patientsOutputDTOs) {
    this.patientsOutputDTO = patientsOutputDTOs;
  }
}
