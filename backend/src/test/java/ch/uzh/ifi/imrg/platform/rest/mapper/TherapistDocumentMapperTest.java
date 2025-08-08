package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistDocumentOutputDTO;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TherapistDocumentMapperTest {

    @Test
    void convertEntityToDTO_mapsAllFields() {
        TherapistDocument entity = new TherapistDocument();
        entity.setId(UUID.randomUUID().toString());
        entity.setFileName("name.pdf");
        entity.setFileType("application/pdf");

        TherapistDocumentOutputDTO dto =
                TherapistDocumentMapper.INSTANCE.convertEntityToTherapistDocumentOutputDTO(entity);

        assertEquals(entity.getId(),       dto.getId());
        assertEquals(entity.getFileName(), dto.getFileName());
        assertEquals(entity.getFileType(), dto.getFileType());
    }
}
