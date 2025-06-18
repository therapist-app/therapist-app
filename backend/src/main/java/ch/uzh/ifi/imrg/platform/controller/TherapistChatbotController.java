package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.TherapistChatbotInputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatbotOutputDTO;
import ch.uzh.ifi.imrg.platform.service.TherapistChatbotService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/therapist-chatbot")
public class TherapistChatbotController {

  private final TherapistChatbotService therapistChatbotService;
  private final TherapistService therapistService;

  public TherapistChatbotController(
      TherapistChatbotService therapistChatbotService, TherapistService therapistService) {
    this.therapistChatbotService = therapistChatbotService;
    this.therapistService = therapistService;
  }

  @PostMapping("/chat")
  @ResponseStatus(HttpStatus.OK)
  public TherapistChatbotOutputDTO chatWithTherapistChatbot(
      @RequestBody TherapistChatbotInputDTO therapistChatbotInputDTO,
      HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return therapistChatbotService.chat(therapistChatbotInputDTO, loggedInTherapist);
  }
}
