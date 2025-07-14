package ch.uzh.ifi.imrg.platform.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

  public static File convertMultiPartFileToFile(MultipartFile file) throws IOException {
    File tempFile = Files.createTempFile("upload-", "-" + file.getOriginalFilename()).toFile();
    try (InputStream is = file.getInputStream()) {
      Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    tempFile.deleteOnExit();

    return tempFile;
  }
}
