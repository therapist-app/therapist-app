package ch.uzh.ifi.imrg.platform;

import ch.uzh.ifi.imrg.platform.security.TherapistIdArgumentResolver;
import ch.uzh.ifi.imrg.platform.utils.RouteLoggingInterceptor;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationTest {

    @Test
    void main_runsSpringApplication() {
        try (MockedStatic<org.springframework.boot.SpringApplication> ms = mockStatic(org.springframework.boot.SpringApplication.class)) {
            ms.when(() -> org.springframework.boot.SpringApplication.run(Application.class, new String[]{})).thenReturn(null);
            Application.main(new String[]{});
            ms.verify(() -> org.springframework.boot.SpringApplication.run(Application.class, new String[]{}));
        }
    }

    @Test
    void helloWorld_returnsExpectedText() {
        assertEquals("The application is running.", new Application().helloWorld());
    }

    @Test
    void webMvcConfigurer_wiresCorsInterceptorAndResolver() {
        RouteLoggingInterceptor interceptor = mock(RouteLoggingInterceptor.class);
        TherapistIdArgumentResolver resolver = mock(TherapistIdArgumentResolver.class);

        var configurer = new Application().webMvcConfigurer(interceptor, resolver);

        CorsRegistry corsRegistry = mock(CorsRegistry.class);
        CorsRegistration corsRegistration = mock(CorsRegistration.class);
        when(corsRegistry.addMapping("/**")).thenReturn(corsRegistration);
        when(corsRegistration.allowedOriginPatterns("*")).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods("*")).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders("*")).thenReturn(corsRegistration);
        when(corsRegistration.allowCredentials(true)).thenReturn(corsRegistration);

        configurer.addCorsMappings(corsRegistry);

        verify(corsRegistry).addMapping("/**");
        verify(corsRegistration).allowedOriginPatterns("*");
        verify(corsRegistration).allowedMethods("*");
        verify(corsRegistration).allowedHeaders("*");
        verify(corsRegistration).allowCredentials(true);

        InterceptorRegistry interceptorRegistry = mock(InterceptorRegistry.class);
        InterceptorRegistration interceptorRegistration = mock(InterceptorRegistration.class);
        when(interceptorRegistry.addInterceptor(interceptor)).thenReturn(interceptorRegistration);
        when(interceptorRegistration.addPathPatterns("/**")).thenReturn(interceptorRegistration);

        configurer.addInterceptors(interceptorRegistry);

        verify(interceptorRegistry).addInterceptor(interceptor);
        verify(interceptorRegistration).addPathPatterns("/**");

        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        configurer.addArgumentResolvers(resolvers);
        assertTrue(resolvers.contains(resolver));
    }

    @Test
    void restTemplate_notNull() {
        assertNotNull(new Application().restTemplate());
    }
}
