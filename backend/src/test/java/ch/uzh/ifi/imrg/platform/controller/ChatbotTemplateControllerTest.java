package ch.uzh.ifi.imrg.platform.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateChatbotTemplateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.ChatbotTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


class ChatbotTemplateControllerTest {

    private MockMvc mvc;
    private ObjectMapper mapper;

    @Mock private ChatbotTemplateService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ChatbotTemplateController controller = new ChatbotTemplateController(service);

        HandlerMethodArgumentResolver therapistIdResolver =
                new HandlerMethodArgumentResolver() {
                    @Override
                    public boolean supportsParameter(MethodParameter parameter) {
                        return parameter.hasParameterAnnotation(CurrentTherapistId.class)
                                && String.class.equals(parameter.getParameterType());
                    }

                    @Override
                    public Object resolveArgument(
                            MethodParameter parameter,
                            @Nullable ModelAndViewContainer mavContainer,
                            NativeWebRequest webRequest,
                            @Nullable WebDataBinderFactory binderFactory) {
                        return "ther-123";
                    }
                };

        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setCustomArgumentResolvers(therapistIdResolver)
                .build();

        mapper = new ObjectMapper();
    }


    @Test
    @DisplayName("GET /chatbot-templates/{id} returns 200 and the template")
    void getTemplate() throws Exception {
        ChatbotTemplateOutputDTO out = new ChatbotTemplateOutputDTO();
        out.setId("tpl-1");
        when(service.getTemplateForTherapist("tpl-1", "ther-123")).thenReturn(out);

        mvc.perform(get("/chatbot-templates/tpl-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("tpl-1"));
    }

    @Test
    @DisplayName("POST /chatbot-templates creates a template")
    void createTemplate() throws Exception {
        CreateChatbotTemplateDTO in = new CreateChatbotTemplateDTO();
        in.setChatbotName("CB");

        ChatbotTemplateOutputDTO out = new ChatbotTemplateOutputDTO();
        out.setId("new-id");

        when(service.createTemplate(any(), eq("ther-123"))).thenReturn(out);

        mvc.perform(
                        post("/chatbot-templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(in)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("new-id"));
    }

    @Nested
    class PatientSpecific {

        @Test
        @DisplayName("POST /chatbot-templates/patients/{patientId}")
        void createTemplateForPatient() throws Exception {
            CreateChatbotTemplateDTO in = new CreateChatbotTemplateDTO();
            in.setChatbotName("CB");

            ChatbotTemplateOutputDTO out = new ChatbotTemplateOutputDTO();
            out.setId("p-tpl");

            when(service.createTemplateForPatient(eq("pat-9"), any(), eq("ther-123"))).thenReturn(out);

            mvc.perform(
                            post("/chatbot-templates/patients/pat-9")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(in)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value("p-tpl"));
        }

        @Test
        @DisplayName("GET /chatbot-templates/patients/{patientId}")
        void listTemplatesForPatient() throws Exception {
            when(service.getTemplatesForPatient("pat-9", "ther-123")).thenReturn(java.util.List.of());

            mvc.perform(get("/chatbot-templates/patients/pat-9")).andExpect(status().isOk());
        }
    }

    @Test
    @DisplayName("DELETE /chatbot-templates/{id}")
    void deleteTemplate() throws Exception {
        mvc.perform(delete("/chatbot-templates/tpl-1")).andExpect(status().isOk());
        verify(service).deleteTemplate("tpl-1", "ther-123");
    }

    @Test
    @DisplayName("DELETE /chatbot-templates/patients/{patientId}/{templateId}")
    void deleteTemplateForPatient() throws Exception {
        mvc.perform(delete("/chatbot-templates/patients/pat-9/tpl-1")).andExpect(status().isOk());
        verify(service).deleteTemplateForPatient("pat-9", "tpl-1", "ther-123");
    }
}
