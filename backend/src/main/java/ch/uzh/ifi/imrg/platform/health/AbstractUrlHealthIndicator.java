package ch.uzh.ifi.imrg.platform.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractUrlHealthIndicator implements HealthIndicator {

  private final RestTemplate restTemplate;

  protected AbstractUrlHealthIndicator(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public Health health() {
    try {
      ResponseEntity<String> response =
          restTemplate.exchange(getUrl(), HttpMethod.GET, createRequestEntity(), String.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        return Health.up().withDetail("url", getUrl()).build();
      } else {
        return Health.down()
            .withDetail("url", getUrl())
            .withDetail("status", response.getStatusCode())
            .withDetail("body", response.getBody())
            .build();
      }
    } catch (ResourceAccessException ex) {
      return Health.down().withDetail("url", getUrl()).withDetail("error", ex.getMessage()).build();
    } catch (Exception ex) {
      return Health.down().withDetail("url", getUrl()).withDetail("error", ex.getMessage()).build();
    }
  }

  protected HttpEntity<String> createRequestEntity() {
    return new HttpEntity<>(new HttpHeaders());
  }

  protected abstract String getUrl();
}
