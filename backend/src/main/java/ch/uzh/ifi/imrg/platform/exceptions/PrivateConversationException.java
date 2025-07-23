package ch.uzh.ifi.imrg.platform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PrivateConversationException extends RuntimeException {
  public PrivateConversationException(String message) {
    super(message);
  }
}
