package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatCompletionWithConfigRequestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatCompletionResponseDTO;
import ch.uzh.ifi.imrg.platform.service.ChatMessageService;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatMessageService chatMessageService;
  private final TherapistService therapistService;

  public ChatController(ChatMessageService chatMessageService, TherapistService therapistService) {
    this.chatMessageService = chatMessageService;
    this.therapistService = therapistService;
  }

  @PostMapping("/completions-with-config")
  @ResponseStatus(HttpStatus.OK)
  public ChatCompletionResponseDTO chatWithConfig(
      @RequestBody ChatCompletionWithConfigRequestDTO request,
      HttpServletRequest httpServletRequest) {
    Therapist loggedInTherapist =
        therapistService.getCurrentlyLoggedInTherapist(httpServletRequest);
    return chatMessageService.chat(request, loggedInTherapist);
  }
}
