package ch.uzh.ifi.imrg.platform.utils;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class DocumentParserUtil {

    private static final Tika tika = new Tika();

    private DocumentParserUtil() {
    }

    public static String extractText(MultipartFile file) {
        try (InputStream stream = file.getInputStream()) {
            return tika.parseToString(stream);
        } catch (Exception e) {
            // Optional: add better logging or rethrow if needed
            return "Text extraction failed";
        }
    }
}
