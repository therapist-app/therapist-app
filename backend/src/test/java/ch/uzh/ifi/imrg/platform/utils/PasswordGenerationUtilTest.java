package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGenerationUtilTest {

    @Test
    void generateUserFriendlyPassword_hasPatternAndLength() {
        for (int i = 0; i < 10; i++) {
            String p = PasswordGenerationUtil.generateUserFriendlyPassword();
            assertNotNull(p);
            assertTrue(p.length() >= 8);
            assertTrue(p.matches("[A-Za-z]+[a-z]+\\d{2}"));
        }
    }
}
