package ch.uzh.ifi.imrg.platform.rest.dto.input;

import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdateTherapistDTOTest {

    @Test
    void allAccessorsAndEquality() {
        UpdateTherapistDTO dto = new UpdateTherapistDTO();
        dto.setLlmModel(LLMModel.AZURE_OPENAI);
        dto.setPassword("secret");

        assertEquals(LLMModel.AZURE_OPENAI, dto.getLlmModel());
        assertEquals("secret",              dto.getPassword());

        UpdateTherapistDTO copy = new UpdateTherapistDTO();
        copy.setLlmModel(LLMModel.AZURE_OPENAI);
        copy.setPassword("secret");

        assertEquals(dto, copy);
        assertEquals(dto.hashCode(), copy.hashCode());
        assertTrue(dto.toString().contains("secret"));
    }
}
