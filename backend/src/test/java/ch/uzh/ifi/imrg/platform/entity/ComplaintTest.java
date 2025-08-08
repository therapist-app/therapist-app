package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ComplaintTest {

    @Test
    void fullCoverage() {
        Complaint complaint = new Complaint();
        Patient patient = mock(Patient.class, RETURNS_DEEP_STUBS);
        when(patient.getTherapist().getId()).thenReturn("tid");
        complaint.setPatient(patient);
        complaint.setMainComplaint("mc");
        complaint.setDuration("d");
        complaint.setOnset("on");
        complaint.setCourse("co");
        complaint.setPrecipitatingFactors("pf");
        complaint.setAggravatingRelieving("ar");
        complaint.setTimeline("tl");
        complaint.setDisturbances("dist");
        complaint.setSuicidalIdeation("si");
        complaint.setNegativeHistory("nh");

        assertEquals("tid", complaint.getOwningTherapistId());

        try (MockedStatic<LLMContextBuilder> mocked = Mockito.mockStatic(LLMContextBuilder.class)) {
            mocked.when(() -> LLMContextBuilder.getOwnProperties(eq(complaint), eq(0)))
                    .thenReturn(new StringBuilder("ctx"));
            assertEquals("ctx", complaint.toLLMContext(0));
        }

        assertEquals(complaint, complaint);
        assertNotEquals(complaint, new Complaint());
        assertEquals(complaint.hashCode(), complaint.hashCode());
        assertNotNull(complaint.toString());
        assertNotNull(complaint.getId());
    }
}
