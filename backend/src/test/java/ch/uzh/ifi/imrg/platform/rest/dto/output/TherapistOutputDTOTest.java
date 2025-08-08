package ch.uzh.ifi.imrg.platform.rest.dto.output;

import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TherapistOutputDTOTest {

    @Test
    void allAccessorsAndEquality() {
        Instant now = Instant.now();

        TherapistOutputDTO dto = new TherapistOutputDTO();
        dto.setId("id");
        dto.setEmail("e@x.ch");
        dto.setLlmModel(LLMModel.LOCAL_UZH);
        dto.setChatbotTemplatesOutputDTO(List.of(new ChatbotTemplateOutputDTO()));
        dto.setPatientsOutputDTO(List.of(new PatientOutputDTO()));
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now.plusSeconds(1));

        assertEquals("id",                  dto.getId());
        assertEquals("e@x.ch",              dto.getEmail());
        assertEquals(LLMModel.LOCAL_UZH,    dto.getLlmModel());
        assertEquals(1,                     dto.getChatbotTemplatesOutputDTO().size());
        assertEquals(1,                     dto.getPatientsOutputDTO().size());
        assertEquals(now,                   dto.getCreatedAt());
        assertEquals(now.plusSeconds(1),    dto.getUpdatedAt());

        TherapistOutputDTO same = new TherapistOutputDTO();
        same.setId("id");
        same.setEmail("e@x.ch");
        same.setLlmModel(LLMModel.LOCAL_UZH);
        same.setChatbotTemplatesOutputDTO(List.of(new ChatbotTemplateOutputDTO()));
        same.setPatientsOutputDTO(List.of(new PatientOutputDTO()));
        same.setCreatedAt(now);
        same.setUpdatedAt(now.plusSeconds(1));

        assertEquals(dto, same);
        assertEquals(dto.hashCode(), same.hashCode());
        assertTrue(dto.toString().contains("e@x.ch"));
    }
}
