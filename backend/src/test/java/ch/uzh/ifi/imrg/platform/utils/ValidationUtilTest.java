package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    void noThrowBranches() {
        ValidationUtil.assertStartBeforeEnd(null, new Date());
        ValidationUtil.assertStartBeforeEnd(new Date(), null);
        Date s = new Date(System.currentTimeMillis());
        Date e = new Date(System.currentTimeMillis() + 1000);
        ValidationUtil.assertStartBeforeEnd(s, e);
    }

    @Test
    void throwsWhenEndNotAfterStart() {
        Date s = new Date();
        Date e1 = new Date(s.getTime());
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> ValidationUtil.assertStartBeforeEnd(s, e1));
        assertTrue(ex1.getMessage().contains("exercise.end_must_be_after_start"));
        Date e2 = new Date(s.getTime() - 1000);
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> ValidationUtil.assertStartBeforeEnd(s, e2));
        assertTrue(ex2.getMessage().contains("exercise.end_must_be_after_start"));
    }

    @Test
    void privateCtorCovered() throws Exception {
        Constructor<ValidationUtil> c = ValidationUtil.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }
}
