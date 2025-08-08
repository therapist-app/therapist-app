package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TherapistDocumentOutputDTOTest {

    @Test
    void gettersAndSetters_workAsExpected() {
        String id       = UUID.randomUUID().toString();
        String fileName = "doc.pdf";
        String fileType = "application/pdf";

        TherapistDocumentOutputDTO dto = new TherapistDocumentOutputDTO();
        dto.setId(id);
        dto.setFileName(fileName);
        dto.setFileType(fileType);

        assertEquals(id,       dto.getId());
        assertEquals(fileName, dto.getFileName());
        assertEquals(fileType, dto.getFileType());

        String expectedToString =
                "TherapistDocumentOutputDTO(id=" + id +
                        ", fileName=" + fileName +
                        ", fileType=" + fileType + ")";
        assertEquals(expectedToString, dto.toString());

        TherapistDocumentOutputDTO same = new TherapistDocumentOutputDTO();
        same.setId(id);
        same.setFileName(fileName);
        same.setFileType(fileType);

        assertEquals(dto, same);
        assertEquals(dto.hashCode(), same.hashCode());
    }
}
