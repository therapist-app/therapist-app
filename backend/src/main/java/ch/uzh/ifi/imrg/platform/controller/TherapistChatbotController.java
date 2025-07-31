package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.TherapistChatbotInputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatbotOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.TherapistChatbotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/therapist-chatbot")
public class TherapistChatbotController {

  private final TherapistChatbotService therapistChatbotService;

  public TherapistChatbotController(TherapistChatbotService therapistChatbotService) {
    this.therapistChatbotService = therapistChatbotService;
  }

  @PostMapping("/chat")
  @ResponseStatus(HttpStatus.OK)
  public TherapistChatbotOutputDTO chatWithTherapistChatbot(
      @Valid @RequestBody TherapistChatbotInputDTO therapistChatbotInputDTO,
      @CurrentTherapistId String therapistId) {
    return therapistChatbotService.chat(therapistChatbotInputDTO, therapistId);
  }
}
