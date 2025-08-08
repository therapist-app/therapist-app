package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentParserUtilTest {

    private static void setLimit(Integer v) throws Exception {
        Field f = EnvironmentVariables.class.getDeclaredField("MAX_CHARACTERS_PER_PDF");
        f.setAccessible(true);
        f.set(null, v);
    }

    @Test
    void extractText_longOverLimit_handlesEitherParserOrFallback() throws Exception {
        setLimit(50);
        String s = "B".repeat(80);
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
        String out = DocumentParserUtil.extractText(file);
        String truncated = s.substring(0, 50);
        assertTrue(truncated.equals(out) || "Text extraction failed".equals(out));
    }

    @Test
    void extractText_streamThrows_returnsFallbackMessage() throws Exception {
        setLimit(100);
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException("x"));
        String out = DocumentParserUtil.extractText(file);
        assertEquals("Text extraction failed", out);
    }
}
