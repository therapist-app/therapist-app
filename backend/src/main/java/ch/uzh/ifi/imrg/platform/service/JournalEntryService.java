package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.CoachGetAllJournalEntriesDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CoachJournalEntryOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JournalEntryService {

  private final PatientRepository patientRepository;

  public JournalEntryService(@Qualifier("patientRepository") PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public List<CoachGetAllJournalEntriesDTOPatientAPI> listAllJournalEntries(
      String patientId, String therapistId) {
    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    return PatientAppAPIs.coachJournalEntryControllerPatientAPI
        .listAll2(patientId)
        .collectList()
        .block();
  }

  public CoachJournalEntryOutputDTOPatientAPI getOneJournalEntry(
      String patientId, String journalEntryId, String therapistId) {
    Patient patient = patientRepository.getReferenceById(patientId);
    SecurityUtil.checkOwnership(patient, therapistId);

    return PatientAppAPIs.coachJournalEntryControllerPatientAPI
        .getOne1(patientId, journalEntryId)
        .block();
  }
}
