package ch.uzh.ifi.imrg.platform.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * An abstract HealthIndicator for services that can be checked via a URL. It sends an HTTP GET
 * request to the target URL to check for a 2xx success status. This version is more robust as it
 * allows for custom headers (e.g., for authentication) and uses GET, which is more widely supported
 * than HEAD.
 */
public abstract class AbstractUrlHealthIndicator implements HealthIndicator {

  private final RestTemplate restTemplate;

  protected AbstractUrlHealthIndicator(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public Health health() {
    try {
      // Use an HTTP GET request and pass the request entity, which may contain auth
      // headers.
      ResponseEntity<String> response =
          restTemplate.exchange(getUrl(), HttpMethod.GET, createRequestEntity(), String.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        return Health.up().withDetail("url", getUrl()).build();
      } else {
        // If the response is not successful, include the status and body for debugging.
        return Health.down()
            .withDetail("url", getUrl())
            .withDetail("status", response.getStatusCode())
            .withDetail("body", response.getBody())
            .build();
      }
    } catch (ResourceAccessException ex) {
      // Catches network errors like "Connection refused".
      return Health.down().withDetail("url", getUrl()).withDetail("error", ex.getMessage()).build();
    } catch (Exception ex) {
      // Catches other exceptions, including HTTP client errors (4xx/5xx).
      return Health.down().withDetail("url", getUrl()).withDetail("error", ex.getMessage()).build();
    }
  }

  /**
   * Creates the HttpEntity for the request. Subclasses can override this to add authentication
   * headers (e.g., Authorization, api-key). By default, it returns an entity with no extra headers.
   *
   * @return HttpEntity with necessary headers.
   */
  protected HttpEntity<String> createRequestEntity() {
    return new HttpEntity<>(new HttpHeaders());
  }

  /**
   * Subclasses must implement this method to provide the specific URL of the service they are
   * checking.
   *
   * @return The URL of the service to check.
   */
  protected abstract String getUrl();
}
