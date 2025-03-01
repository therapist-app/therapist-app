package ch.uzh.ifi.imrg.platform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {

  private static final String BASE64_SECRET_KEY = "this-will-be-added-as-an-en-variable";

  // Decode the BASE64_SECRET_KEY to generate the SecretKey
  private static SecretKey getSecretKey() {
    byte[] keyBytes = Decoders.BASE64.decode(BASE64_SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public static String createJWT(String email) {
    long nowMillis = System.currentTimeMillis();
    long expMillis = nowMillis + 30L * 24 * 60 * 60 * 1000; // 30 days
    Date now = new Date(nowMillis);
    Date exp = new Date(expMillis);

    SecretKey key = getSecretKey();

    String jwt =
        Jwts.builder().subject(email).issuedAt(now).expiration(exp).signWith(key).compact();
    return jwt;
  }

  public static void addJwtCookie(HttpServletResponse response, String jwt) {
    Cookie cookie = new Cookie("auth", jwt);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  public static String getJwtFromCookie(HttpServletRequest request) {

    return null;
  }

  public static String validateJWTAndExtractEmail(HttpServletRequest request) {

    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      throw new Error("Cookie could not be found");
    }

    for (Cookie cookie : cookies) {
      if ("auth".equals(cookie.getName())) {
        String jwt = cookie.getValue();
        SecretKey key = getSecretKey();

        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt);

        String jwtEmail = claims.getPayload().getSubject();

        Date expiration = claims.getPayload().getExpiration();
        if (expiration.before(new Date())) {
          throw new Error("The JWT has expired");
        }
        return jwtEmail;
      }
    }
    throw new Error("Cookie 'auth' could not be found");
  }
}
