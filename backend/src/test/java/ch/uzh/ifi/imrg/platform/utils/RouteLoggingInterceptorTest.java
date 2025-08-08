package ch.uzh.ifi.imrg.platform.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RouteLoggingInterceptorTest {

    @Test
    void preHandle_logsAndReturnsTrue() {
        RouteLoggingInterceptor i = new RouteLoggingInterceptor();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        when(req.getMethod()).thenReturn("GET");
        when(req.getRequestURI()).thenReturn("/x");
        boolean out = i.preHandle(req, res, new Object());
        assertTrue(out);
        verify(req).getMethod();
        verify(req).getRequestURI();
    }
}
