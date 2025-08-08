package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatePatientDocumentFromTherapistDocumentDTOTest {

    @Test
    void gettersAndSetters() {
        CreatePatientDocumentFromTherapistDocumentDTO dto =
                new CreatePatientDocumentFromTherapistDocumentDTO();
        dto.setTherapistDocumentId("td");
        dto.setPatientId("pid");

        assertEquals("td", dto.getTherapistDocumentId());
        assertEquals("pid", dto.getPatientId());
    }
}
