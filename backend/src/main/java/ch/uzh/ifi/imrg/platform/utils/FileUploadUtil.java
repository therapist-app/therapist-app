package ch.uzh.ifi.imrg.platform.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

public class FileUploadUtil {

  public static void uploadFile(String path, MultipartFile file) throws IOException {
    String boundary = "--------------------------" + UUID.randomUUID().toString().replace("-", "");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    String lineEnd = "\r\n";

    bos.write(("--" + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
    bos.write(
        ("Content-Disposition: form-data; name=\"file\"; filename=\""
                + file.getOriginalFilename()
                + "\""
                + lineEnd)
            .getBytes(StandardCharsets.UTF_8));
    bos.write(
        ("Content-Type: " + file.getContentType() + lineEnd).getBytes(StandardCharsets.UTF_8));
    bos.write(lineEnd.getBytes(StandardCharsets.UTF_8));
    bos.write(file.getBytes());
    bos.write(lineEnd.getBytes(StandardCharsets.UTF_8));
    bos.write(("--" + boundary + "--" + lineEnd).getBytes(StandardCharsets.UTF_8));

    byte[] multipartBody = bos.toByteArray();

    WebClient webClient = WebClient.builder().baseUrl(PatientAppAPIs.PATIENT_APP_URL).build();

    String contentTypeHeader = "multipart/form-data; boundary=" + boundary;

    webClient
        .post()
        .uri(path)
        .header(HttpHeaders.CONTENT_TYPE, contentTypeHeader)
        .header("X-Coach-Key", PatientAppAPIs.COACH_ACCESS_KEY)
        .contentLength(multipartBody.length)
        .bodyValue(multipartBody)
        .retrieve()
        .toBodilessEntity()
        .block();
  }
}
