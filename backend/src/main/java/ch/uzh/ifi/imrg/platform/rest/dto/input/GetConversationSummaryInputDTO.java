package ch.uzh.ifi.imrg.platform.rest.dto.input;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetConversationSummaryInputDTO {
  @NotNull private Instant start;

  @NotNull private Instant end;
}
