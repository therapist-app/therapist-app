package ch.uzh.ifi.imrg.platform.health;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.platform.utils.EnvironmentVariables;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

class HealthIndicatorsTest {

    private static class TestIndicator extends AbstractUrlHealthIndicator {
        TestIndicator(RestTemplate rt) { super(rt); }
        @Override protected String getUrl() { return "http://test"; }
    }

    @Test
    void upBranch() {
        var rt = mock(RestTemplate.class);
        when(rt.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("ok"));
        Health h = new TestIndicator(rt).health();
        assertEquals(Status.UP, h.getStatus());
    }

    @Test
    void downNon2xxBranch() {
        var rt = mock(RestTemplate.class);
        when(rt.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad"));
        Health h = new TestIndicator(rt).health();
        assertEquals(Status.DOWN, h.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST, h.getDetails().get("status"));
    }

    @Test
    void resourceAccessBranch() {
        var rt = mock(RestTemplate.class);
        when(rt.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenThrow(new ResourceAccessException("io"));
        Health h = new TestIndicator(rt).health();
        assertEquals(Status.DOWN, h.getStatus());
        assertTrue(h.getDetails().get("error").toString().contains("io"));
    }

    @Test
    void exceptionBranchAndPatientAppIndicator() throws Exception {
        var rt = mock(RestTemplate.class);
        when(rt.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenThrow(new RuntimeException("err"));
        Health h = new TestIndicator(rt).health();
        assertEquals(Status.DOWN, h.getStatus());

        Field f = EnvironmentVariables.class.getDeclaredField("PATIENT_APP_URL");
        f.setAccessible(true);
        f.set(null, "http://patient");
        when(rt.exchange(eq("http://patient"), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("x"));
        var p = new PatientAppHealthIndicator(rt).health();
        assertEquals(Status.UP, p.getStatus());
    }
}
