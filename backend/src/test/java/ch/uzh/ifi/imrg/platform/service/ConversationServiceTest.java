package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.api.CoachChatbotControllerPatientAPI;
import ch.uzh.ifi.imrg.platform.exceptions.PrivateConversationException;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ConversationSummaryOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConversationServiceTest {

    private static final String TH_ID = "thera-1";
    private static final String PT_ID = "patient-42";
    private static final Instant START = Instant.parse("2025-01-01T00:00:00Z");
    private static final Instant END   = Instant.parse("2025-01-31T23:59:59Z");

    private final PatientRepository repo = Mockito.mock(PatientRepository.class);
    private final ConversationService service = new ConversationService(repo);

    private static AutoCloseable swapStaticClient(CoachChatbotControllerPatientAPI client) throws Exception {
        Field f = PatientAppAPIs.class.getField("coachChatbotControllerPatientAPI");
        Object old = f.get(null);
        f.setAccessible(true);
        f.set(null, client);
        return () -> { try { f.set(null, old); } catch (IllegalAccessException ignored) {} };
    }

    @Test
    @DisplayName("guard: patient not linked to therapist")
    void ownershipGuard() {
        Mockito.when(repo.existsByIdAndTherapistId(PT_ID, TH_ID)).thenReturn(false);
        assertThatThrownBy(() -> service.getConversationSummary(PT_ID, TH_ID, START, END))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("remote 404 ⇒ empty string")
    void remote404() throws Exception {
        Mockito.when(repo.existsByIdAndTherapistId(PT_ID, TH_ID)).thenReturn(true);

        WebClientResponseException notFound =
                WebClientResponseException.create(
                        HttpStatus.NOT_FOUND.value(), "nf", HttpHeaders.EMPTY,
                        new byte[0], StandardCharsets.UTF_8, null);

        CoachChatbotControllerPatientAPI api = Mockito.mock(CoachChatbotControllerPatientAPI.class);
        Mockito.when(api.getConversationSummary(Mockito.any(), Mockito.eq(PT_ID)))
                .thenReturn(Mono.error(notFound));

        try (AutoCloseable ignored = swapStaticClient(api)) {
            ConversationSummaryOutputDTO dto =
                    service.getConversationSummary(PT_ID, TH_ID, START, END);
            assertThat(dto.getConversationSummary()).isEmpty();
        }
    }

    @Test
    @DisplayName("remote 403 ⇒ PrivateConversationException")
    void remote403() throws Exception {
        Mockito.when(repo.existsByIdAndTherapistId(PT_ID, TH_ID)).thenReturn(true);

        WebClientResponseException forbidden =
                WebClientResponseException.create(
                        HttpStatus.FORBIDDEN.value(), "fb", HttpHeaders.EMPTY,
                        new byte[0], StandardCharsets.UTF_8, null);

        CoachChatbotControllerPatientAPI api = Mockito.mock(CoachChatbotControllerPatientAPI.class);
        Mockito.when(api.getConversationSummary(Mockito.any(), Mockito.eq(PT_ID)))
                .thenReturn(Mono.error(forbidden));

        try (AutoCloseable ignored = swapStaticClient(api)) {
            assertThatThrownBy(() -> service.getConversationSummary(PT_ID, TH_ID, START, END))
                    .isInstanceOf(PrivateConversationException.class);
        }
    }
}
