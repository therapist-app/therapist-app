package ch.uzh.ifi.imrg.platform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    private SecretKey key;

    @BeforeEach
    void setUp() {
        byte[] bytes = new byte[32];
        for (int i = 0; i < bytes.length; i++) bytes[i] = (byte) (i + 1);
        String b64 = Base64.getEncoder().encodeToString(bytes);
        EnvironmentVariables.JWT_SECRET_KEY = b64;
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(EnvironmentVariables.JWT_SECRET_KEY));
    }

    @Test
    void createAndValidate_ok() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        String jwt = JwtUtil.createJWT("a@b.com");
        when(req.getCookies()).thenReturn(new Cookie[]{new Cookie("authTherapistApp", jwt)});
        String email = JwtUtil.validateJWTAndExtractEmail(req);
        assertEquals("a@b.com", email);

        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
        assertEquals("a@b.com", claims.getSubject());
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    void validate_noCookies_unauthorized() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getCookies()).thenReturn(null);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> JwtUtil.validateJWTAndExtractEmail(req));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Cookie could not be found"));
    }

    @Test
    void validate_missingAuthCookie_unauthorized() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getCookies()).thenReturn(new Cookie[]{new Cookie("other", "x")});
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> JwtUtil.validateJWTAndExtractEmail(req));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertTrue(ex.getReason().contains("could not be found"));
    }

    @Test
    void validate_invalidJwt_unauthorized() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getCookies()).thenReturn(new Cookie[]{new Cookie("authTherapistApp", "bad")});
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> JwtUtil.validateJWTAndExtractEmail(req));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertTrue(ex.getReason().contains("problem"));
    }

    @Test
    void validate_expiredJwt_unauthorized() {
        Date past = Date.from(Instant.now().minusSeconds(60));
        String expired = Jwts.builder().subject("x@y.com").issuedAt(past).expiration(past).signWith(key).compact();
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getCookies()).thenReturn(new Cookie[]{new Cookie("authTherapistApp", expired)});
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> JwtUtil.validateJWTAndExtractEmail(req));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertTrue(ex.getReason().contains("problem"));
    }

    @Test
    void addAndRemoveCookie_secureFalseTrue() {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req = mock(HttpServletRequest.class);

        EnvironmentVariables.IS_PRODUCTION = false;
        JwtUtil.addJwtCookie(resp, req, "jwt");
        ArgumentCaptor<Cookie> captor1 = ArgumentCaptor.forClass(Cookie.class);
        verify(resp).addCookie(captor1.capture());
        Cookie c1 = captor1.getValue();
        assertEquals("authTherapistApp", c1.getName());
        assertEquals("jwt", c1.getValue());
        assertTrue(c1.isHttpOnly());
        assertEquals("/", c1.getPath());
        assertFalse(c1.getSecure());
        assertTrue(c1.getMaxAge() >= 60 * 60 * 24);

        reset(resp);

        EnvironmentVariables.IS_PRODUCTION = true;
        JwtUtil.removeJwtCookie(resp);
        ArgumentCaptor<Cookie> captor2 = ArgumentCaptor.forClass(Cookie.class);
        verify(resp).addCookie(captor2.capture());
        Cookie c2 = captor2.getValue();
        assertEquals("authTherapistApp", c2.getName());
        assertNull(c2.getValue());
        assertTrue(c2.isHttpOnly());
        assertEquals("/", c2.getPath());
        assertTrue(c2.getSecure());
        assertEquals(0, c2.getMaxAge());
    }
}
