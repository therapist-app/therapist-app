package ch.uzh.ifi.imrg.platform.rest.dto.output;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientDocumentOutputDTOTest {

    @Test
    void gettersAndSetters() {
        PatientDocumentOutputDTO dto = new PatientDocumentOutputDTO();
        dto.setId("id");
        dto.setIsSharedWithPatient(Boolean.TRUE);
        dto.setFileName("file");
        dto.setFileType("type");

        assertEquals("id", dto.getId());
        assertTrue(dto.getIsSharedWithPatient());
        assertEquals("file", dto.getFileName());
        assertEquals("type", dto.getFileType());
    }
}
