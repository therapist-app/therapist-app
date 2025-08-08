package ch.uzh.ifi.imrg.platform.rest.dto.input;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CoachGetAllJournalEntriesDTOTest {

    @Test
    void gettersAndSetters() {
        CoachGetAllJournalEntriesDTO dto = new CoachGetAllJournalEntriesDTO();
        String id = "entry1";
        Instant created = Instant.parse("2025-01-01T10:15:30.00Z");
        Instant updated = Instant.parse("2025-02-02T11:20:35.00Z");
        String title = "My Title";
        Set<String> tags = Set.of("tag1", "tag2");

        dto.setId(id);
        dto.setCreatedAt(created);
        dto.setUpdatedAt(updated);
        dto.setTitle(title);
        dto.setTags(tags);

        assertEquals(id, dto.getId());
        assertEquals(created, dto.getCreatedAt());
        assertEquals(updated, dto.getUpdatedAt());
        assertEquals(title, dto.getTitle());
        assertEquals(tags, dto.getTags());
    }

    @Test
    void nullValues() {
        CoachGetAllJournalEntriesDTO dto = new CoachGetAllJournalEntriesDTO();

        dto.setId(null);
        dto.setCreatedAt(null);
        dto.setUpdatedAt(null);
        dto.setTitle(null);
        dto.setTags(null);

        assertNull(dto.getId());
        assertNull(dto.getCreatedAt());
        assertNull(dto.getUpdatedAt());
        assertNull(dto.getTitle());
        assertNull(dto.getTags());
    }
}
