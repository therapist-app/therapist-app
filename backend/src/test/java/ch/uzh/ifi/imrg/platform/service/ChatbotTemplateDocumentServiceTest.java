package ch.uzh.ifi.imrg.platform.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.generated.api.CoachChatbotControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.model.ChatbotConfigurationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.UpdateChatbotDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import ch.uzh.ifi.imrg.platform.entity.*;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.utils.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.tika.parser.AutoDetectParser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ChatbotTemplateDocumentServiceTest {

    private ChatbotTemplateRepository templateRepo;
    private ChatbotTemplateDocumentRepository docRepo;
    private TherapistRepository therapistRepo;
    private ChatbotTemplateDocumentService service;

    private final Therapist therapist = new Therapist();
    private final Patient patient = new Patient();
    private final ChatbotTemplate template = new ChatbotTemplate();

    @BeforeEach
    void setUp() {
        templateRepo = mock(ChatbotTemplateRepository.class);
        docRepo = mock(ChatbotTemplateDocumentRepository.class);
        therapistRepo = mock(TherapistRepository.class);

        therapist.setId("ther-1");
        patient.setId("pat-1");
        template.setId("tmpl-1");
        template.setActive(true);
        template.setTherapist(therapist);
        template.setPatient(patient);

        when(templateRepo.findByIdAndTherapistId("tmpl-1", "ther-1"))
                .thenReturn(Optional.of(template));
        when(docRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service = new ChatbotTemplateDocumentService(templateRepo, docRepo, therapistRepo);
    }

    @Test
    void readFullText_fallbackPath() throws Exception {
        try (MockedConstruction<AutoDetectParser> parser =
                     mockConstruction(
                             AutoDetectParser.class,
                             (mock, ctx) -> doThrow(new RuntimeException()).when(mock)
                                     .parse(any(), any(), any(), any()));
             MockedStatic<DocumentParserUtil> fallback = mockStatic(DocumentParserUtil.class)) {

            fallback.when(() -> DocumentParserUtil.extractText(any())).thenReturn("fallback");
            MockMultipartFile file =
                    new MockMultipartFile("f", "y.pdf", "application/pdf", new byte[] {1});

            Method m =
                    ChatbotTemplateDocumentService.class.getDeclaredMethod(
                            "readFullText", MultipartFile.class);
            m.setAccessible(true);

            assertThat(m.invoke(service, file)).isEqualTo("fallback");
            fallback.verify(() -> DocumentParserUtil.extractText(file));
        }
    }

    @Test
    void upload_shortText_updatesChatbot_withContext() {
        MockMultipartFile file =
                new MockMultipartFile("f", "a.txt", "text", "short".getBytes(StandardCharsets.UTF_8));

        ChatbotConfigurationOutputDTOPatientAPI cfg = new ChatbotConfigurationOutputDTOPatientAPI();
        cfg.setId("cfg-1");

        CoachChatbotControllerPatientAPI api = mock(CoachChatbotControllerPatientAPI.class);
        PatientAppAPIs.coachChatbotControllerPatientAPI = api;
        when(api.getChatbotConfigurations("pat-1")).thenReturn(Flux.just(cfg));
        when(api.updateChatbot(anyString(), any())).thenReturn(Mono.empty());

        try (MockedStatic<LLMContextBuilder> ctx = mockStatic(LLMContextBuilder.class)) {
            ctx.when(
                            () ->
                                    LLMContextBuilder.addLLMContextOfListOfEntities(
                                            any(), anyList(), anyString(), anyInt()))
                    .thenAnswer(inv -> null);

            service.uploadChatbotTemplateDocument("tmpl-1", file, "ther-1");

            verify(api).updateChatbot(eq("pat-1"), any(UpdateChatbotDTOPatientAPI.class));
        }
    }


    @Test
    void updateChatbot_returnsImmediately_whenTemplateInactive() throws Exception {
        ChatbotTemplate inactive = new ChatbotTemplate();
        inactive.setActive(false);
        inactive.setPatient(patient);
        Method m =
                ChatbotTemplateDocumentService.class.getDeclaredMethod(
                        "updateChatbot", ChatbotTemplate.class);
        m.setAccessible(true);
        m.invoke(service, inactive);
    }

    @Test
    void updateChatbot_noDocuments_firstConfigNull() throws Exception {
        CoachChatbotControllerPatientAPI api = mock(CoachChatbotControllerPatientAPI.class);
        PatientAppAPIs.coachChatbotControllerPatientAPI = api;
        when(api.getChatbotConfigurations("pat-1")).thenReturn(Flux.empty());

        ChatbotTemplate noDocs = new ChatbotTemplate();
        noDocs.setPatient(patient);
        noDocs.setActive(true);
        noDocs.setChatbotRole("role");
        noDocs.setChatbotTone("tone");
        noDocs.setWelcomeMessage("welcome");

        Method m =
                ChatbotTemplateDocumentService.class.getDeclaredMethod(
                        "updateChatbot", ChatbotTemplate.class);
        m.setAccessible(true);
        m.invoke(service, noDocs);

        verify(api).getChatbotConfigurations("pat-1");
        verify(api, never()).updateChatbot(anyString(), any());
    }


    @Test
    void getDocumentsOfTemplate_and_downloadWork() {
        ChatbotTemplateDocument d = new ChatbotTemplateDocument();
        d.setId("doc-1");
        d.setFileName("n");
        d.setFileType("t");
        template.getChatbotTemplateDocuments().add(d);

        try (MockedStatic<SecurityUtil> sec = mockStatic(SecurityUtil.class)) {
            when(templateRepo.findById("tmpl-1")).thenReturn(Optional.of(template));
            var list = service.getDocumentsOfTemplate("tmpl-1", "ther-1");
            assertThat(list).singleElement().satisfies(dto -> assertThat(dto.getId()).isEqualTo("doc-1"));

            when(docRepo.findById("doc-1")).thenReturn(Optional.of(d));
            assertThat(service.downloadChatbotTemplateDocument("doc-1", "ther-1")).isSameAs(d);
        }
    }


    @Test
    void deleteFile_onInactiveTemplate_hitsEarlyReturnInsideUpdateChatbot() {
        ChatbotTemplateDocument doc = new ChatbotTemplateDocument();
        doc.setChatbotTemplate(template);
        template.setActive(false);
        template.getChatbotTemplateDocuments().add(doc);

        when(docRepo.findById("xx")).thenReturn(Optional.of(doc));
        service.deleteFile("xx", "ther-1");
        assertThat(template.getChatbotTemplateDocuments()).isEmpty();
    }
}
