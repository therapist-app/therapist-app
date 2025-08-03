package ch.uzh.ifi.imrg.platform.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Define a standard, reusable error response structure. A record is perfect for this.
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

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllUncaughtException(Exception ex, WebRequest request) {
    log.error("An unexpected error occurred", ex);
    var errorResponse = new ApiErrorResponse("An unexpected internal server error occurred.", null);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
