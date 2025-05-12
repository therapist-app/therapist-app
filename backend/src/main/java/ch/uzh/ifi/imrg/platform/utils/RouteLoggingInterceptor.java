package ch.uzh.ifi.imrg.platform.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RouteLoggingInterceptor implements HandlerInterceptor {
  private static final Logger log = LoggerFactory.getLogger(RouteLoggingInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
    log.info("{} {}", req.getMethod(), req.getRequestURI());
    return true;
  }
}
