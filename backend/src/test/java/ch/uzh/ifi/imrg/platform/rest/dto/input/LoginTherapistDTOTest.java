package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginTherapistDTOTest {

    @Test
    void allAccessorsAndEquality() {
        LoginTherapistDTO dto = new LoginTherapistDTO();
        dto.setEmail("login@x.ch");
        dto.setPassword("pw");

        assertEquals("login@x.ch", dto.getEmail());
        assertEquals("pw",         dto.getPassword());

        LoginTherapistDTO same = new LoginTherapistDTO();
        same.setEmail("login@x.ch");
        same.setPassword("pw");

        assertEquals(dto, same);
        assertEquals(dto.hashCode(), same.hashCode());
        assertTrue(dto.toString().contains("login@x.ch"));
    }
}
