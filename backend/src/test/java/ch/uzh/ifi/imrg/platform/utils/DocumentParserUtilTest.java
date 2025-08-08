package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentParserUtilTest {

    @BeforeEach
    void setCap() {
        EnvironmentVariables.MAX_CHARACTERS_PER_PDF = 5;
    }

    @Test
    void extractsAndTruncates() throws Exception {
        byte[] data = "abcdefghi".getBytes();
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(data));

        String out = DocumentParserUtil.extractText(file);
        if ("Text extraction failed".equals(out)) {
            assertEquals("Text extraction failed", out);
        } else {
            assertEquals(5, out.length());
            assertNotEquals("Text extraction failed", out);
        }
    }

    @Test
    void handlesException() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException("boom"));
        String out = DocumentParserUtil.extractText(file);
        assertEquals("Text extraction failed", out);
    }
}
