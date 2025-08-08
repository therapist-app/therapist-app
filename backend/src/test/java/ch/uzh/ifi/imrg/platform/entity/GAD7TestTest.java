package ch.uzh.ifi.imrg.platform.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GAD7TestTest {

    @Test
    void fullCoverage() {
        GAD7Test test = new GAD7Test();
        Patient patient = mock(Patient.class, RETURNS_DEEP_STUBS);
        when(patient.getTherapist().getId()).thenReturn("tid");
        test.setPatient(patient);
        test.setQuestion1(1);
        test.setQuestion2(2);
        test.setQuestion3(3);
        test.setQuestion4(4);
        test.setQuestion5(5);
        test.setQuestion6(6);
        test.setQuestion7(7);

        assertEquals("tid", test.getOwningTherapistId());

        assertEquals(test, test);
        assertNotEquals(test, new GAD7Test());
        assertEquals(test.hashCode(), test.hashCode());
        assertNotNull(test.toString());
        assertNotNull(test.getTestId());
    }
}
