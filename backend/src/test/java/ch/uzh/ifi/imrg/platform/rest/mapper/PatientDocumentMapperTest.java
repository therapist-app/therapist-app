package ch.uzh.ifi.imrg.platform.rest.mapper;

import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientDocumentMapperTest {

    @Test
    void mappingWorks() {
        PatientDocument pd = new PatientDocument();
        pd.setIsSharedWithPatient(Boolean.TRUE);
        pd.setFileName("f"); pd.setFileType("t");
        assertEquals("f",
                PatientDocumentMapper.INSTANCE
                        .convertEntityToPatientDocumentOutputDTO(pd)
                        .getFileName());
    }
}
