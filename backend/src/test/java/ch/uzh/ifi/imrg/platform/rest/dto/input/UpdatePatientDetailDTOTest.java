package ch.uzh.ifi.imrg.platform.rest.dto.input;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdatePatientDetailDTOTest {

    @Test
    void settersAndGetters() {
        UpdatePatientDetailDTO dto = new UpdatePatientDetailDTO();
        dto.setId("id");
        dto.setName("n");
        dto.setEmail("m");
        dto.setAge(0);
        dto.setComplaints(List.of(new ComplaintDTO()));

        assertEquals("id", dto.getId());
        assertEquals("n", dto.getName());
        assertEquals("m", dto.getEmail());
        assertEquals(0, dto.getAge());
        assertEquals(1, dto.getComplaints().size());
    }
}
