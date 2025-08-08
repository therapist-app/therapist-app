package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateTherapistDTOTest {

    @Test
    void allAccessorsAndEquality() {
        CreateTherapistDTO dto = new CreateTherapistDTO();
        dto.setEmail("new@x.ch");
        dto.setPassword("pw");

        assertEquals("new@x.ch", dto.getEmail());
        assertEquals("pw",       dto.getPassword());

        CreateTherapistDTO same = new CreateTherapistDTO();
        same.setEmail("new@x.ch");
        same.setPassword("pw");

        assertEquals(dto, same);
        assertEquals(dto.hashCode(), same.hashCode());
        assertTrue(dto.toString().contains("new@x.ch"));
    }
}
