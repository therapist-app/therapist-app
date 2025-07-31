package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatCompletionWithConfigRequestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatCompletionResponseDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.ChatMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatMessageService chatMessageService;

  public ChatController(ChatMessageService chatMessageService) {
    this.chatMessageService = chatMessageService;
  }

  @PostMapping("/completions-with-config")
  @ResponseStatus(HttpStatus.OK)
  public ChatCompletionResponseDTO chatWithConfig(
      @Valid @RequestBody ChatCompletionWithConfigRequestDTO request,
      @CurrentTherapistId String therapistId) {
    return chatMessageService.chat(request, therapistId);
  }
}
