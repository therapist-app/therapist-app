package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.Language;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import lombok.Data;

@Data
public class TherapistOutputDTO {
  private String id;
  private String email;
  private Language language;
  private List<ChatbotTemplateOutputDTO> chatbotTemplatesOutputDTO;
  private List<PatientOutputDTO> patientsOutputDTO;
  private Instant createdAt;
  private Instant updatedAt;

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
