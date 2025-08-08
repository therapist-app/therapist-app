package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PatientDocumentTest {

    @Test
    void allBranchesCovered() {
        Therapist therapist = new Therapist();
        therapist.setId("tid");

        Patient patient = new Patient();
        patient.setTherapist(therapist);

        PatientDocument doc = new PatientDocument();
        doc.setPatient(patient);
        doc.setIsSharedWithPatient(Boolean.TRUE);
        doc.setFileName("file.pdf");
        doc.setFileType("application/pdf");
        doc.setExternalId("ext");
        doc.setFileData("x".getBytes());
        doc.setExtractedText("text");
        doc.setCreatedAt(Instant.now());
        doc.setUpdatedAt(Instant.now());

        assertEquals("tid", doc.getOwningTherapistId());

        try (MockedStatic<LLMContextBuilder> mocked =
                     Mockito.mockStatic(LLMContextBuilder.class)) {
            mocked
                    .when(() -> LLMContextBuilder.getOwnProperties(doc, 0))
                    .thenReturn(new StringBuilder("CTX"));
            assertEquals("CTX", doc.toLLMContext(0));
        }
    }
}
