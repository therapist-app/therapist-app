package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.output.ConversationSummaryOutputDTO;
import ch.uzh.ifi.imrg.platform.service.ConversationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ConversationControllerTest {

    @Test
    @DisplayName("getConversationSummary delegates to service with correct args")
    void delegation() {
        ConversationService service = Mockito.mock(ConversationService.class);
        ConversationController controller = new ConversationController(service);

        Instant start = Instant.parse("2025-01-01T00:00:00Z");
        Instant end   = Instant.parse("2025-01-31T23:59:59Z");

        ConversationSummaryOutputDTO expected = new ConversationSummaryOutputDTO();
        expected.setConversationSummary("dummy");
        Mockito.when(service.getConversationSummary("p1", "t1", start, end))
                .thenReturn(expected);

        ConversationSummaryOutputDTO actual =
                controller.getConversationSummary("p1", "t1", start, end);

        assertThat(actual).isSameAs(expected);
        Mockito.verify(service).getConversationSummary("p1", "t1", start, end);
    }
}
