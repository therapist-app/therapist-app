package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.ConversationSummaryOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.ConversationService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
public class ConversationController {

  private final ConversationService conversationService;

  @GetMapping("/patients/{patientId}/summary")
  @ResponseStatus(HttpStatus.OK)
  public ConversationSummaryOutputDTO getConversationSummary(
      @PathVariable String patientId,
      @CurrentTherapistId String therapistId,
      @RequestParam Instant start,
      @RequestParam Instant end) {

    return conversationService.getConversationSummary(patientId, therapistId, start, end);
  }
}
