package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateGAD7TestDTOTest {

    @Test
    void gettersSettersToStringEqualsHashCode() {
        CreateGAD7TestDTO dto1 = new CreateGAD7TestDTO();
        dto1.setPatientId("p");
        dto1.setQuestion1(0);
        dto1.setQuestion2(1);
        dto1.setQuestion3(2);
        dto1.setQuestion4(3);
        dto1.setQuestion5(0);
        dto1.setQuestion6(1);
        dto1.setQuestion7(2);
        assertEquals("p", dto1.getPatientId());
        assertEquals(0, dto1.getQuestion1());
        assertEquals(1, dto1.getQuestion2());
        assertEquals(2, dto1.getQuestion3());
        assertEquals(3, dto1.getQuestion4());
        assertEquals(0, dto1.getQuestion5());
        assertEquals(1, dto1.getQuestion6());
        assertEquals(2, dto1.getQuestion7());
        String s = dto1.toString();
        assertTrue(s.contains("patientId"));
        assertTrue(s.contains("question1"));
        assertTrue(s.contains("question7"));

        CreateGAD7TestDTO dto2 = new CreateGAD7TestDTO();
        dto2.setPatientId("p");
        dto2.setQuestion1(0);
        dto2.setQuestion2(1);
        dto2.setQuestion3(2);
        dto2.setQuestion4(3);
        dto2.setQuestion5(0);
        dto2.setQuestion6(1);
        dto2.setQuestion7(2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setQuestion7(3);
        assertNotEquals(dto1, dto2);
    }
}
