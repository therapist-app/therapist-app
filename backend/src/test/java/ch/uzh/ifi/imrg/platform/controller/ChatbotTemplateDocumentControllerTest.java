package ch.uzh.ifi.imrg.platform.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateDocumentOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.ChatbotTemplateDocumentService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

class ChatbotTemplateDocumentControllerTest {

    private static final String THERAPIST_HEADER = "X-THERAPIST-ID";

    private static class TherapistIdHeaderResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(CurrentTherapistId.class)
                    && parameter.getParameterType().equals(String.class);
        }

        @Override
        public Object resolveArgument(
                MethodParameter parameter,
                ModelAndViewContainer mavContainer,
                NativeWebRequest webRequest,
                WebDataBinderFactory binderFactory) {
            return webRequest.getHeader(THERAPIST_HEADER);
        }
    }

    private ChatbotTemplateDocumentService service;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(ChatbotTemplateDocumentService.class);
        ChatbotTemplateDocumentController controller =
                new ChatbotTemplateDocumentController(service);

        mvc =
                MockMvcBuilders.standaloneSetup(controller)
                        .setCustomArgumentResolvers(new TherapistIdHeaderResolver())
                        .build();
    }


    @Test
    void createDocument_uploadEndpoint_returns201() throws Exception {
        MockMultipartFile multipart =
                new MockMultipartFile(
                        "file",
                        "doc.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        "content".getBytes(StandardCharsets.UTF_8));

        mvc.perform(
                        multipart("/chatbot-template-documents/{templateId}", "tmp-1")
                                .file(multipart)
                                .header(THERAPIST_HEADER, "ther-1"))
                .andExpect(status().isCreated());

        verify(service).uploadChatbotTemplateDocument(eq("tmp-1"), any(), eq("ther-1"));
    }

    @Test
    void getDocuments_returnsDtoList() throws Exception {
        ChatbotTemplateDocumentOutputDTO dto = new ChatbotTemplateDocumentOutputDTO();
        dto.setId("d1");
        dto.setFileName("doc.txt");
        dto.setFileType(MediaType.TEXT_PLAIN_VALUE);

        when(service.getDocumentsOfTemplate("tmp-2", "ther-2")).thenReturn(List.of(dto));

        mvc.perform(
                        get("/chatbot-template-documents/{templateId}", "tmp-2")
                                .header(THERAPIST_HEADER, "ther-2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("d1"));

        verify(service).getDocumentsOfTemplate("tmp-2", "ther-2");
    }

    @Test
    void downloadDocument_streamsBinaryWithHeaders() throws Exception {
        byte[] data = "hello".getBytes(StandardCharsets.UTF_8);
        var doc = new ch.uzh.ifi.imrg.platform.entity.ChatbotTemplateDocument();
        doc.setFileData(data);
        doc.setFileName("file.txt");
        doc.setFileType(MediaType.TEXT_PLAIN_VALUE);

        when(service.downloadChatbotTemplateDocument("doc-3", "ther-3")).thenReturn(doc);

        mvc.perform(
                        get("/chatbot-template-documents/{id}/download", "doc-3")
                                .header(THERAPIST_HEADER, "ther-3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"file.txt\""))
                .andExpect(content().bytes(data));

        verify(service).downloadChatbotTemplateDocument("doc-3", "ther-3");
    }

    @Test
    void deleteDocument_callsService() throws Exception {
        mvc.perform(
                        delete("/chatbot-template-documents/{id}", "doc-4")
                                .header(THERAPIST_HEADER, "ther-4"))
                .andExpect(status().isOk());

        verify(service).deleteFile("doc-4", "ther-4");
    }
}
