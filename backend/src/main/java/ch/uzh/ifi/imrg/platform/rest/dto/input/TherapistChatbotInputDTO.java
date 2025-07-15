package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.util.List;

import ch.uzh.ifi.imrg.platform.enums.Language;
import lombok.Data;

@Data
public class TherapistChatbotInputDTO {
  private List<ChatMessageDTO> chatMessages;
  private String patientId;
  private Language language;
}
