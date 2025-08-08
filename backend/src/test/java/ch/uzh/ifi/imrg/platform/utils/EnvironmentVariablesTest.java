package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnvironmentVariablesTest {

    @Test
    void constructorSetsStaticsAndProdFlag() {
        new EnvironmentVariables(
                "http://p", "jwt", "model", "url", "key",
                "prod", "az_ep", "az_key", "dep", 12345, 99);

        assertEquals("jwt", EnvironmentVariables.JWT_SECRET_KEY);
        assertEquals("model", EnvironmentVariables.LOCAL_LLM_MODEL);
        assertEquals("url", EnvironmentVariables.LOCAL_LLM_URL);
        assertEquals("key", EnvironmentVariables.LOCAL_LLM_API_KEY);
        assertEquals("http://p", EnvironmentVariables.PATIENT_APP_URL);
        assertEquals("az_ep", EnvironmentVariables.AZURE_OPENAI_ENDPOINT);
        assertEquals("az_key", EnvironmentVariables.AZURE_OPENAI_API_KEY);
        assertEquals("dep", EnvironmentVariables.AZURE_OPENAI_DEPLOYMENT_NAME);
        assertEquals(12345, EnvironmentVariables.LLM_MAX_CHARACTERS.intValue());
        assertEquals(99, EnvironmentVariables.MAX_CHARACTERS_PER_PDF.intValue());
        assertTrue(EnvironmentVariables.IS_PRODUCTION);

        new EnvironmentVariables(
                "http://p2", "jwt2", "m2", "u2", "k2",
                "dev", "ep2", "ak2", "d2", 1, 2);
        assertFalse(EnvironmentVariables.IS_PRODUCTION);
        assertEquals("jwt2", EnvironmentVariables.JWT_SECRET_KEY);
    }
}
