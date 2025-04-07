package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TherapistOutputDTO {
  private String id;
  private String email;
  private String workspaceId;
  private List<ChatbotTemplateOutputDTO> chatbotTemplatesOutputDTO;
  private List<PatientOutputDTO> patientsOutputDTO;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public TherapistOutputDTO sortDTO() {
    chatbotTemplatesOutputDTO.sort(
        Comparator.comparing(
            ChatbotTemplateOutputDTO::getCreatedAt,
            Comparator.nullsLast(Comparator.reverseOrder())));
    patientsOutputDTO.sort(
        Comparator.comparing(
            PatientOutputDTO::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())));
    return this;
  }
}
