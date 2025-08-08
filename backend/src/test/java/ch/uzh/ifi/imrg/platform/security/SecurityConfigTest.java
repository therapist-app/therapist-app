package ch.uzh.ifi.imrg.platform.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    @Test
    void buildsFilterChainWithPermissiveConfig() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class);
        DefaultSecurityFilterChain chain = mock(DefaultSecurityFilterChain.class);

        when(http.authorizeHttpRequests(any())).thenReturn(http);
        when(http.csrf(any())).thenReturn(http);
        when(http.build()).thenReturn(chain);

        SecurityConfig cfg = new SecurityConfig();
        SecurityFilterChain result = cfg.securityFilterChain(http);

        assertSame(chain, result);
        verify(http).authorizeHttpRequests(any());
        verify(http).csrf(any());
        verify(http).build();
    }
}
