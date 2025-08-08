package ch.uzh.ifi.imrg.platform.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

class GlobalExceptionAdviceTest {

    private final GlobalExceptionAdvice advice = new GlobalExceptionAdvice();
    private final WebRequest request = mock(WebRequest.class);

    @Test
    void coversAllBranches() {
        var binding = new BeanPropertyBindingResult(new Object(), "obj");
        binding.addError(new FieldError("obj", "field", "invalid"));
        var manve = new MethodArgumentNotValidException(null, binding);
        var res =
                advice.handleMethodArgumentNotValid(manve, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        var body = (ApiErrorResponse) res.getBody();
        assertEquals("invalid", body.details().get("field"));

        var rsex = new ResponseStatusException(HttpStatus.NOT_FOUND, "missing");
        res = advice.handleResponseStatusException(rsex, request);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertEquals("missing", ((ApiErrorResponse) res.getBody()).message());

        res = advice.handleConflict(new IllegalArgumentException("bad"), request);
        assertEquals(HttpStatus.CONFLICT, res.getStatusCode());

        res = advice.handleEntityNotFound(new EntityNotFoundException("nf"), request);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());

        res = advice.handleDataIntegrityViolation(new DataIntegrityViolationException("dupe"), request);
        assertEquals(HttpStatus.CONFLICT, res.getStatusCode());

        res = advice.handleAccessDenied(new AccessDeniedException("no"), request);
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());

        var hmnre = new HttpMessageNotReadableException("bad", null, null);
        res =
                advice.handleHttpMessageNotReadable(
                        hmnre, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());

        String okJson = "{\"message\":\"client fail\"}";
        var wcre =
                WebClientResponseException.create(
                        502, "Bad", HttpHeaders.EMPTY, okJson.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        res = advice.handleWebClientResponseException(wcre, request);
        assertEquals("client fail", ((ApiErrorResponse) res.getBody()).message());

        var wcre2 =
                WebClientResponseException.create(
                        502, "Bad", HttpHeaders.EMPTY, "xxx".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        res = advice.handleWebClientResponseException(wcre2, request);
        assertTrue(((ApiErrorResponse) res.getBody()).message().contains("502"));

        res = advice.handlePrivateConversationException(new PrivateConversationException("priv"), request);
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());

        res = advice.handleAllUncaughtException(new RuntimeException("err"), request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }
}
