package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.generated.model.DocumentOverviewDTOPatientAPI;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FileUploadUtilTest {

    @Test
    void uploadFile_buildsMultipartAndCallsWebClient() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("f.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getBytes()).thenReturn("hello".getBytes());

        WebClient.Builder b = mock(WebClient.Builder.class);
        WebClient client = mock(WebClient.class);
        WebClient.RequestBodyUriSpec reqUri = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec reqBody = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> reqHeaders = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec resp = mock(WebClient.ResponseSpec.class);

        try (MockedStatic<WebClient> ms = mockStatic(WebClient.class)) {
            ms.when(WebClient::builder).thenReturn(b);
            when(b.baseUrl(any())).thenReturn(b);
            when(b.build()).thenReturn(client);

            when(client.post()).thenReturn(reqUri);
            when(reqUri.uri("/p")).thenReturn(reqBody);
            when(reqBody.header(eq(HttpHeaders.CONTENT_TYPE), anyString())).thenReturn(reqBody);
            when(reqBody.header(eq("X-Coach-Key"), any())).thenReturn(reqBody);
            when(reqBody.contentLength(anyLong())).thenReturn(reqBody);
            doReturn((WebClient.RequestHeadersSpec) reqHeaders).when(reqBody).bodyValue(any());

            when(reqHeaders.retrieve()).thenReturn(resp);

            DocumentOverviewDTOPatientAPI expected = new DocumentOverviewDTOPatientAPI();
            when(resp.bodyToMono(DocumentOverviewDTOPatientAPI.class)).thenReturn(Mono.just(expected));

            DocumentOverviewDTOPatientAPI out = FileUploadUtil.uploadFile("/p", file);
            assertSame(expected, out);
        }
    }
}
