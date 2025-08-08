package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.ChatbotTemplate;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.rest.dto.output.ChatbotTemplateOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TherapistMapperTest {

    private final TherapistMapper mapper = TherapistMapper.INSTANCE;

    @Test
    void mapChatbotTemplates_handlesNullAndNonNull() {
        assertNull(mapper.mapChatbotTemplates(null));

        List<ChatbotTemplateOutputDTO> out =
                mapper.mapChatbotTemplates(List.of(new ChatbotTemplate()));
        assertEquals(1, out.size());
        assertNotNull(out.get(0));
    }

    @Test
    void mapPatients_handlesNullAndNonNull() {
        assertNull(mapper.mapPatients(null));

        List<PatientOutputDTO> out = mapper.mapPatients(List.of(new Patient()));
        assertEquals(1, out.size());
        assertNotNull(out.get(0));
    }
}
