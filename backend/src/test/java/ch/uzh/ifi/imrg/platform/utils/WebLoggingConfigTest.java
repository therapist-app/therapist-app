package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class WebLoggingConfigTest {

    private static Object getField(Object target, String name) {
        Class<?> c = target.getClass();
        while (c != null) {
            try {
                Field f = c.getDeclaredField(name);
                f.setAccessible(true);
                return f.get(target);
            } catch (NoSuchFieldException ignored) {
                c = c.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Field not found: " + name);
    }

    @Test
    void requestLoggingFilter_configuredAsExpected() {
        WebLoggingConfig cfg = new WebLoggingConfig();
        CommonsRequestLoggingFilter f = cfg.requestLoggingFilter();

        assertEquals(true, getField(f, "includeClientInfo"));
        assertEquals(true, getField(f, "includeQueryString"));
        assertEquals(false, getField(f, "includePayload"));
        assertEquals(false, getField(f, "includeHeaders"));
        assertEquals(1024, getField(f, "maxPayloadLength"));
        assertEquals("Incoming request  — ", getField(f, "afterMessagePrefix"));
    }
}
