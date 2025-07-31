package ch.uzh.ifi.imrg.platform.utils;

import java.io.InputStream;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

public class DocumentParserUtil {

  private static final Tika tika = new Tika();

  private DocumentParserUtil() {}

  public static String extractText(MultipartFile file) {
    try (InputStream stream = file.getInputStream()) {
      String text = tika.parseToString(stream);
      if (text.length() > EnvironmentVariables.MAX_CHARACTERS_PER_PDF) {
        return text.substring(0, EnvironmentVariables.MAX_CHARACTERS_PER_PDF);
      } else {
        return text;
      }
    } catch (Exception e) {
      // Optional: add better logging or rethrow if needed
      return "Text extraction failed";
    }
  }
}
