package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.TherapistChatCompletionDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatCompletionOutputDTO;
import ch.uzh.ifi.imrg.platform.service.TherapistChatMessageService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/therapist-chat")
public class TherapistChatController {

  private final TherapistChatMessageService therapistChatMessageService;
  private final TherapistService therapistService;

  public TherapistChatController(
      TherapistChatMessageService therapistChatMessageService, TherapistService therapistService) {
    this.therapistChatMessageService = therapistChatMessageService;
    this.therapistService = therapistService;
  }

  @PostMapping("/completions")
  @ResponseStatus(HttpStatus.OK)
  public TherapistChatCompletionOutputDTO therapistChat(
      @RequestBody TherapistChatCompletionDTO therapistChatCompletionDTO,
      HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return therapistChatMessageService.chat(therapistChatCompletionDTO, loggedInTherapist);
  }
}
