package ch.uzh.ifi.imrg.platform.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

record ApiErrorResponse(String message, Map<String, String> details) {}

@ControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              fieldErrors.put(fieldName, errorMessage);
            });

    var errorResponse =
        new ApiErrorResponse("Validation failed. Please check the provided data.", fieldErrors);
    log.warn("Validation error: {}", fieldErrors);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Object> handleResponseStatusException(
      ResponseStatusException ex, WebRequest request) {
    var errorResponse = new ApiErrorResponse(ex.getReason(), null);

    log.warn(
        "Handled ResponseStatusException: Status {}, Reason '{}'",
        ex.getStatusCode(),
        ex.getReason());

    return new ResponseEntity<>(errorResponse, ex.getStatusCode());
  }

  @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    var errorResponse = new ApiErrorResponse(ex.getMessage(), null);
    log.warn("Business logic conflict: {}", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFound(
      EntityNotFoundException ex, WebRequest request) {
    String errorMessage = "The requested resource could not be found.";

    log.warn("{}: {}", errorMessage, ex.getMessage());

    var errorResponse = new ApiErrorResponse(errorMessage, null);

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolation(
      DataIntegrityViolationException ex, WebRequest request) {
    String message = "A record with this value already exists. Please use a different value.";
    log.warn("{}: {}", message, ex.getMessage());
    var errorResponse = new ApiErrorResponse(message, null);
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
    String message = "You do not have permission to perform this action.";
    log.warn("{}: {}", message, ex.getMessage());
    var errorResponse = new ApiErrorResponse(message, null);
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    String message = "The request body is malformed or contains invalid data types.";
    log.warn("{}: {}", message, ex.getMessage());
    var errorResponse = new ApiErrorResponse(message, null);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(WebClientResponseException.class)
  public ResponseEntity<Object> handleWebClientResponseException(
      WebClientResponseException ex, WebRequest request) {

    ObjectMapper objectMapper = new ObjectMapper();
    String responseBody = ex.getResponseBodyAsString();

    String finalMessage =
        String.format("The client app returned an error with status %s.", ex.getStatusCode());

    try {

      Map<String, Object> bodyAsMap =
          objectMapper.readValue(
              responseBody,
              new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
      finalMessage = (String) bodyAsMap.getOrDefault("message", finalMessage);
    } catch (JsonProcessingException e) {
      log.warn("Could not parse error response from client app: {}", responseBody);
    }

    log.error(
        "Error from client app: Status {}, Message: '{}'", ex.getStatusCode(), finalMessage, ex);

    var errorResponse = new ApiErrorResponse(finalMessage, null);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
  }

  @ExceptionHandler(PrivateConversationException.class)
  public ResponseEntity<Object> handlePrivateConversationException(
      PrivateConversationException ex, WebRequest request) {
    String message = ex.getMessage();
    log.warn("Forbidden access attempt: {}", message);
    var errorResponse = new ApiErrorResponse(message, null);
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllUncaughtException(Exception ex, WebRequest request) {
    log.error("An unexpected error occurred", ex);
    var errorResponse = new ApiErrorResponse("An unexpected internal server error occurred.", null);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
