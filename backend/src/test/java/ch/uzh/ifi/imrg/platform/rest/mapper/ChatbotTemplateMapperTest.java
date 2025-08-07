package ch.uzh.ifi.imrg.platform.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateChatbotTemplateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateChatbotTemplateDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChatbotTemplateMapperTest {

    private final ChatbotTemplateMapper mapper = ChatbotTemplateMapper.INSTANCE;

    @Test
    @DisplayName("Mapping an entity *with* patient & therapist → patientId/therapistId are present")
    void toOutputDTO_withPatientAndTherapist() {
        Patient pat     = new Patient();    pat.setId("pat-9");
        Therapist ther  = new Therapist();  ther.setId("ther-42");

        ChatbotTemplate tpl = new ChatbotTemplate();
        tpl.setChatbotName("CB");
        tpl.setChatbotIcon("data:img/png;base64,…");
        tpl.setChatbotRole("coach");
        tpl.setChatbotTone("friendly");
        tpl.setWelcomeMessage("Hi!");
        tpl.setActive(true);
        tpl.setCreatedAt(Instant.now());
        tpl.setUpdatedAt(Instant.now());
        tpl.setPatient(pat);
        tpl.setTherapist(ther);

        ChatbotTemplateOutputDTO dto = mapper.convertEntityToChatbotTemplateOutputDTO(tpl);

        assertThat(dto.getId()).isEqualTo(tpl.getId());
        assertThat(dto.getIsActive()).isTrue();
        assertThat(dto.getPatientId()).isEqualTo("pat-9");
        assertThat(dto.getTherapistId()).isEqualTo("ther-42");
    }

    @Test
    @DisplayName("Mapping an entity *without* patient & therapist → patientId/therapistId are null")
    void toOutputDTO_withoutPatientAndTherapist() {
        ChatbotTemplate tpl = new ChatbotTemplate();
        tpl.setChatbotName("Solo");
        tpl.setActive(false);

        ChatbotTemplateOutputDTO dto = mapper.convertEntityToChatbotTemplateOutputDTO(tpl);

        assertThat(dto.getIsActive()).isFalse();
        assertThat(dto.getPatientId()).isNull();
        assertThat(dto.getTherapistId()).isNull();
    }

    @Test
    @DisplayName("create-DTO → entity (all fields copied, id generated, lists empty)")
    void createDTO_toEntity() {
        CreateChatbotTemplateDTO in = new CreateChatbotTemplateDTO();
        in.setChatbotName("Creator");
        in.setChatbotIcon("icon");
        in.setChatbotRole("assistant");
        in.setChatbotTone("calm");
        in.setWelcomeMessage("Hello");
        in.setIsActive(true);

        ChatbotTemplate out = mapper.convertCreateChatbotTemplateDTOtoEntity(in);

        assertThat(out.getChatbotName()).isEqualTo("Creator");
        assertThat(out.getChatbotIcon()).isEqualTo("icon");
        assertThat(out.getChatbotRole()).isEqualTo("assistant");
        assertThat(out.getChatbotTone()).isEqualTo("calm");
        assertThat(out.getWelcomeMessage()).isEqualTo("Hello");
        assertThat(out.isActive()).isTrue();

        assertThat(out.getId()).isNotNull();
        assertThat(out.getChatbotTemplateDocuments()).isEmpty();
        assertThat(out.getPatient()).isNull();
        assertThat(out.getTherapist()).isNull();
    }

    @Test
    @DisplayName("update-DTO → entity (icon intentionally ignored by mapper)")
    void updateDTO_toEntity() {
        UpdateChatbotTemplateDTO in = new UpdateChatbotTemplateDTO();
        in.setChatbotName("Updated");
        in.setChatbotRole("support");
        in.setChatbotTone("serious");
        in.setWelcomeMessage("Good day");
        in.setIsActive(false);

        ChatbotTemplate out = mapper.convertUpdateChatbotTemplateDTOtoEntity(in);

        assertThat(out.getChatbotName()).isEqualTo("Updated");
        assertThat(out.getChatbotRole()).isEqualTo("support");
        assertThat(out.getChatbotTone()).isEqualTo("serious");
        assertThat(out.getWelcomeMessage()).isEqualTo("Good day");
        assertThat(out.isActive()).isFalse();

        assertThat(out.getChatbotIcon()).isNull();
    }
}

