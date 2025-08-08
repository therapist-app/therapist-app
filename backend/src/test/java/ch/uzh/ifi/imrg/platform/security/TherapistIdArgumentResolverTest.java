package ch.uzh.ifi.imrg.platform.security;

import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TherapistIdArgumentResolverTest {

    static class SampleController {
        void m1(@CurrentTherapistId String therapistId) {}
        void m2(String noAnnotation) {}
    }

    private MethodParameter param(String method, int index) throws NoSuchMethodException {
        return new MethodParameter(SampleController.class.getDeclaredMethod(method, String.class), index);
    }

    @Test
    void supportsParameterTrueFalse() throws Exception {
        TherapistService svc = mock(TherapistService.class);
        TherapistIdArgumentResolver resolver = new TherapistIdArgumentResolver(svc);

        assertTrue(resolver.supportsParameter(param("m1", 0)));
        assertFalse(resolver.supportsParameter(param("m2", 0)));
    }

    @Test
    void resolveArgumentReturnsServiceValue() throws Exception {
        TherapistService svc = mock(TherapistService.class);
        TherapistIdArgumentResolver resolver = new TherapistIdArgumentResolver(svc);

        NativeWebRequest web = mock(NativeWebRequest.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(web.getNativeRequest(HttpServletRequest.class)).thenReturn(req);
        when(svc.getTherapistIdBasedOnRequest(req)).thenReturn("tid-123");

        Object out = resolver.resolveArgument(param("m1", 0), null, web, null);
        assertEquals("tid-123", out);
    }

    @Test
    void resolveArgumentThrowsWhenNoRequest() {
        TherapistService svc = mock(TherapistService.class);
        TherapistIdArgumentResolver resolver = new TherapistIdArgumentResolver(svc);

        NativeWebRequest web = mock(NativeWebRequest.class);
        when(web.getNativeRequest(HttpServletRequest.class)).thenReturn(null);

        assertThrows(IllegalStateException.class,
                () -> resolver.resolveArgument(param("m1", 0), null, web, null));
    }
}
