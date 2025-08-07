package ch.uzh.ifi.imrg.platform.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.imrg.generated.model.ChatbotConfigurationOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.ChatbotTemplateRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Flux;
import ch.uzh.ifi.imrg.generated.api.CoachChatbotControllerPatientAPI;
import reactor.core.publisher.Mono;
import org.junit.jupiter.api.AfterAll;
import reactor.core.scheduler.Schedulers;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ChatbotTemplateServiceTest {

    @Mock ChatbotTemplateRepository tplRepo;
    @Mock PatientRepository patRepo;

    @InjectMocks ChatbotTemplateService service;

    Therapist therapist;
    Patient patient;
    ChatbotTemplate tpl1;
    ChatbotTemplate tpl2;

    @AfterAll
    static void tearDownSchedulers() {
        Schedulers.shutdownNow();
        Schedulers.resetFactory();
    }

    @BeforeEach
    void data() throws Exception {
        therapist = new Therapist();
        therapist.setId("ther-1");

        patient = new Patient();
        patient.setId("pat-1");
        patient.setTherapist(therapist);

        tpl1 = new ChatbotTemplate();
        tpl1.setId("tpl-1");
        tpl1.setTherapist(therapist);
        tpl1.setPatient(patient);
        tpl1.setActive(true);
        tpl1.setUpdatedAt(Instant.parse("2025-01-01T00:00:00Z"));

        tpl2 = new ChatbotTemplate();
        tpl2.setId("tpl-2");
        tpl2.setTherapist(therapist);
        tpl2.setPatient(patient);
        tpl2.setActive(false);
        tpl2.setUpdatedAt(Instant.parse("2025-02-01T00:00:00Z"));

        mockPatientAppBridge();
    }

    private void mockPatientAppBridge() throws Exception {
        CoachChatbotControllerPatientAPI apiMock = mock(CoachChatbotControllerPatientAPI.class);

        lenient().when(apiMock.createChatbot(anyString(), any())).thenReturn(Mono.empty());
        lenient().when(apiMock.updateChatbot(anyString(), any())).thenReturn(Mono.empty());
        lenient().when(apiMock.getChatbotConfigurations(anyString()))
                .thenReturn(Flux.just(new ChatbotConfigurationOutputDTOPatientAPI()
                        .id("remote-id")
                        .chatbotRole("role")
                        .chatbotTone("tone")
                        .welcomeMessage("welcome")));

        Field f = PatientAppAPIs.class.getField("coachChatbotControllerPatientAPI");
        f.setAccessible(true);
        f.set(null, apiMock);
    }

    @Test
    @DisplayName("getTemplateForTherapist – happy path")
    void getTemplate_ok() {
        when(tplRepo.findById("tpl-1")).thenReturn(Optional.of(tpl1));

        assertThat(service.getTemplateForTherapist("tpl-1", "ther-1").getId()).isEqualTo("tpl-1");
    }

    @Test
    @DisplayName("getTemplateForTherapist – not found branch")
    void getTemplate_notFound() {
        when(tplRepo.findById("nope")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getTemplateForTherapist("nope", "ther-1"))
                .isInstanceOf(jakarta.persistence.EntityNotFoundException.class);

    }

    @Test
    void createTemplateForPatient_firstTemplate_setsActiveAndCallsRemote() {
        when(patRepo.existsByIdAndTherapistId("pat-1", "ther-1")).thenReturn(true);
        when(patRepo.getPatientById("pat-1")).thenReturn(patient);
        when(tplRepo.countByPatientId("pat-1")).thenReturn(0L);
        when(tplRepo.save(any(ChatbotTemplate.class))).thenAnswer(i -> i.getArgument(0));

        var dto = service.createTemplateForPatient("pat-1", new ChatbotTemplate(), "ther-1");

        assertThat(dto.getIsActive()).isTrue();
        verify(tplRepo).flush();
    }

    @Test
    void createTemplateForPatient_wrongTherapist_throws() {
        when(patRepo.existsByIdAndTherapistId("pat-1", "ther-1")).thenReturn(false);
        assertThatThrownBy(
                () -> service.createTemplateForPatient("pat-1", new ChatbotTemplate(), "ther-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class UpdateTemplateBranches {

        @Test
        @DisplayName("updateTemplate – therapist-only template (patient == null)")
        void therapistOnlyBranch() {
            ChatbotTemplate tpl = new ChatbotTemplate();
            tpl.setId("x");
            tpl.setTherapist(therapist);

            when(tplRepo.findByIdAndTherapistId("x", "ther-1")).thenReturn(Optional.of(tpl));
            when(tplRepo.save(tpl)).thenReturn(tpl);

            service.updateTemplate("x", tpl, "ther-1");

            verify(tplRepo).save(tpl);
        }

        @Test
        @DisplayName("updateTemplate – cannot deactivate sole template")
        void onlyTemplateCantDeactivate() {
            when(tplRepo.findByIdAndTherapistId("tpl-1", "ther-1")).thenReturn(Optional.of(tpl1));
            when(tplRepo.findByPatientId("pat-1")).thenReturn(List.of(tpl1));

            ChatbotTemplate in = new ChatbotTemplate();
            in.setActive(false);

            assertThatThrownBy(() -> service.updateTemplate("tpl-1", in, "ther-1"))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("updateTemplate – deactivate active template, another becomes active")
        void deactivateSwitch() {
            when(tplRepo.findByIdAndTherapistId("tpl-1", "ther-1")).thenReturn(Optional.of(tpl1));
            when(tplRepo.findByPatientId("pat-1")).thenReturn(List.of(tpl1, tpl2));

            ChatbotTemplate in = new ChatbotTemplate();
            in.setActive(false);

            service.updateTemplate("tpl-1", in, "ther-1");

            assertThat(tpl1.isActive()).isFalse();
            assertThat(tpl2.isActive()).isTrue();
            verify(tplRepo, times(2)).save(any(ChatbotTemplate.class));
            verify(tplRepo).flush();
        }

        @Test
        @DisplayName("updateTemplate – activate inactive template, others set inactive")
        void activateTurnsOffOthers() {
            when(tplRepo.findByIdAndTherapistId("tpl-2", "ther-1")).thenReturn(Optional.of(tpl2));
            when(tplRepo.findByPatientId("pat-1")).thenReturn(List.of(tpl1, tpl2));

            ChatbotTemplate in = new ChatbotTemplate();
            in.setActive(true);

            service.updateTemplate("tpl-2", in, "ther-1");

            assertThat(tpl2.isActive()).isTrue();
            assertThat(tpl1.isActive()).isFalse();
        }
    }

    @Test
    void deleteTemplate_lastBranch_notFound() {
        when(tplRepo.findByIdAndTherapistId("x", "ther-1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteTemplate("x", "ther-1"))
                .isInstanceOf(Error.class);
    }

    @Test
    void deleteTemplateForPatient_lastTemplate_throws() {
        when(patRepo.findById("pat-1")).thenReturn(Optional.of(patient));
        when(tplRepo.findByIdAndPatientId("tpl-1", "pat-1")).thenReturn(Optional.of(tpl1));
        when(tplRepo.findByPatientId("pat-1")).thenReturn(List.of(tpl1));

        assertThatThrownBy(() -> service.deleteTemplateForPatient("pat-1", "tpl-1", "ther-1"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deleteTemplateForPatient_activeRemoved_newActiveSelected() {
        when(patRepo.findById("pat-1")).thenReturn(Optional.of(patient));
        when(tplRepo.findByIdAndPatientId("tpl-1", "pat-1")).thenReturn(Optional.of(tpl1));
        when(tplRepo.findByPatientId("pat-1")).thenReturn(List.of(tpl1, tpl2))
                .thenReturn(List.of(tpl2));

        service.deleteTemplateForPatient("pat-1", "tpl-1", "ther-1");

        assertThat(tpl2.isActive()).isTrue();
        verify(tplRepo, atLeastOnce()).flush();
    }

    @Test
    void cloneTemplate_ok() {
        when(tplRepo.findByIdAndTherapistId("tpl-1", "ther-1")).thenReturn(Optional.of(tpl1));
        when(tplRepo.save(any(ChatbotTemplate.class)))
                .thenAnswer(inv -> inv.<ChatbotTemplate>getArgument(0));

        var dto = service.cloneTemplate("tpl-1", "ther-1");

        assertThat(dto.getChatbotName()).contains("Clone");
    }

    @Test
    void cloneTemplateForPatient_ok() {
        when(patRepo.findById("pat-1")).thenReturn(Optional.of(patient));
        when(tplRepo.findById("tpl-1")).thenReturn(Optional.of(tpl1));
        when(tplRepo.save(any(ChatbotTemplate.class))).thenAnswer(i -> i.getArgument(0));

        var dto = service.cloneTemplateForPatient("pat-1", "tpl-1", "ther-1");

        assertThat(dto.getPatientId()).isEqualTo("pat-1");
    }
}
