package ch.uzh.ifi.imrg.platform.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static File convertMultiPartFileToFile(MultipartFile multipartFile) {
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(convertedFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert MultipartFile to File", e);
        }
        return convertedFile;
    }
}
