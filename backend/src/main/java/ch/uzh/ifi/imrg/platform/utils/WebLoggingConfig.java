package ch.uzh.ifi.imrg.platform.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class WebLoggingConfig {

  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeClientInfo(true);
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(false);
    filter.setIncludeHeaders(false);
    filter.setMaxPayloadLength(1024);
    filter.setAfterMessagePrefix("Incoming request  — ");
    return filter;
  }
}
