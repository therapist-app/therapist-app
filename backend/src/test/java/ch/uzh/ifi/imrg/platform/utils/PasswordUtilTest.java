package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void encryptAndCheck_roundtripAndNegative() {
        String raw = "secret123!";
        String hash = PasswordUtil.encryptPassword(raw);
        assertNotNull(hash);
        assertNotEquals(raw, hash);
        assertTrue(PasswordUtil.checkPassword(raw, hash));
        assertFalse(PasswordUtil.checkPassword("wrong", hash));
    }

    @Test
    void constructor_throws() throws Exception {
        Constructor<PasswordUtil> ctor = PasswordUtil.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(InvocationTargetException.class, ctor::newInstance);
        assertTrue(ex.getCause() instanceof UnsupportedOperationException);
    }
}
