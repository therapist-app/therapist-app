package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.generated.model.CoachGetAllJournalEntriesDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CoachJournalEntryOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.security.CurrentTherapistId;
import ch.uzh.ifi.imrg.platform.service.JournalEntryService;
import com.azure.core.annotation.Get;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal-entries")
public class JournalEntryController {

  private final JournalEntryService journalEntryService;

  public JournalEntryController(JournalEntryService journalEntryService) {
    this.journalEntryService = journalEntryService;
  }

  @Get("/{patientId}")
  @ResponseStatus(HttpStatus.OK)
  public List<CoachGetAllJournalEntriesDTOPatientAPI> listAllJournalEntries(
      @PathVariable String patientId, @CurrentTherapistId String therapistId) {
    return journalEntryService.listAllJournalEntries(patientId, therapistId);
  }

  @Get("/{patientId}/{journalEntryId}")
  @ResponseStatus(HttpStatus.OK)
  public CoachJournalEntryOutputDTOPatientAPI getOneJournalEntry(
      @PathVariable String patientId,
      @PathVariable String journalEntryId,
      @CurrentTherapistId String therapistId) {
    return journalEntryService.getOneJournalEntry(patientId, journalEntryId, therapistId);
  }
}
