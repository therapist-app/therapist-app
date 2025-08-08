package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdateExerciseComponentDTOTest {

    @Test
    void gettersSettersEqualsHashCodeToString() {
        UpdateExerciseComponentDTO a = new UpdateExerciseComponentDTO();
        UpdateExerciseComponentDTO b = new UpdateExerciseComponentDTO();

        a.setId("id1");
        a.setExerciseComponentDescription("desc");
        a.setYoutubeUrl("url");
        a.setOrderNumber(3);

        b.setId("id1");
        b.setExerciseComponentDescription("desc");
        b.setYoutubeUrl("url");
        b.setOrderNumber(3);

        assertEquals("id1", a.getId());
        assertEquals("desc", a.getExerciseComponentDescription());
        assertEquals("url", a.getYoutubeUrl());
        assertEquals(3, a.getOrderNumber());
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertTrue(a.toString().contains("orderNumber"));

        b.setOrderNumber(4);
        assertNotEquals(a, b);
    }
}
