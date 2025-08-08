package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileUtilTest {

    @Test
    void convertMultiPartFileToFile_copiesAndReturnsTemp() throws Exception {
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.getOriginalFilename()).thenReturn("a.txt");
        when(mf.getInputStream()).thenReturn(new ByteArrayInputStream("data".getBytes()));
        File f = FileUtil.convertMultiPartFileToFile(mf);
        assertTrue(f.exists());
        assertEquals("data", Files.readString(f.toPath()));
        assertTrue(f.getName().contains("a.txt"));
        assertTrue(f.delete() || !f.exists());
    }
}
