package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.generated.model.CoachGetAllJournalEntriesDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CoachJournalEntryOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.service.JournalEntryService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JournalEntryControllerTest {

    @Test
    void listAllJournalEntries() {
        JournalEntryService service = mock(JournalEntryService.class);
        JournalEntryController controller = new JournalEntryController(service);
        String patientId = "p1";
        String therapistId = "t1";
        List<CoachGetAllJournalEntriesDTOPatientAPI> expected = List.of(new CoachGetAllJournalEntriesDTOPatientAPI());
        when(service.listAllJournalEntries(patientId, therapistId)).thenReturn(expected);
        List<CoachGetAllJournalEntriesDTOPatientAPI> actual = controller.listAllJournalEntries(patientId, therapistId);
        assertEquals(expected, actual);
        verify(service).listAllJournalEntries(patientId, therapistId);
    }

    @Test
    void getOneJournalEntry() {
        JournalEntryService service = mock(JournalEntryService.class);
        JournalEntryController controller = new JournalEntryController(service);
        String patientId = "p1";
        String journalEntryId = "j1";
        String therapistId = "t1";
        CoachJournalEntryOutputDTOPatientAPI expected = new CoachJournalEntryOutputDTOPatientAPI();
        when(service.getOneJournalEntry(patientId, journalEntryId, therapistId)).thenReturn(expected);
        CoachJournalEntryOutputDTOPatientAPI actual = controller.getOneJournalEntry(patientId, journalEntryId, therapistId);
        assertEquals(expected, actual);
        verify(service).getOneJournalEntry(patientId, journalEntryId, therapistId);
    }
}
