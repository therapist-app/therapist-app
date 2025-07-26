package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class TherapistOutputDTO {
  private String id;
  private String email;
  private LLMModel llmModel;
  private List<ChatbotTemplateOutputDTO> chatbotTemplatesOutputDTO;
  private List<PatientOutputDTO> patientsOutputDTO;
  private Instant createdAt;
  private Instant updatedAt;
}
