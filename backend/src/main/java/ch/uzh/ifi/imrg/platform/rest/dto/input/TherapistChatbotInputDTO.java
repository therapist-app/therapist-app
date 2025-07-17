package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.Language;
import java.util.List;
import lombok.Data;

@Data
public class TherapistChatbotInputDTO {
  private List<ChatMessageDTO> chatMessages;
  private String patientId;
  private Language language;
}
