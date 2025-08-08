package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.api.CoachJournalEntryControllerPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CoachGetAllJournalEntriesDTOPatientAPI;
import ch.uzh.ifi.imrg.generated.model.CoachJournalEntryOutputDTOPatientAPI;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JournalEntryServiceTest {
    private PatientRepository patientRepository;
    private JournalEntryService journalEntryService;
    private Patient patient;

    @BeforeEach
    void init() {
        patientRepository = mock(PatientRepository.class);
        journalEntryService = new JournalEntryService(patientRepository);
        patient = mock(Patient.class);
    }

    @Test
    void listAllJournalEntries() {
        String patientId = "p1";
        String therapistId = "t1";
        when(patientRepository.getReferenceById(patientId)).thenReturn(patient);
        CoachGetAllJournalEntriesDTOPatientAPI dto = new CoachGetAllJournalEntriesDTOPatientAPI();
        List<CoachGetAllJournalEntriesDTOPatientAPI> expected = List.of(dto);
        CoachJournalEntryControllerPatientAPI api = mock(CoachJournalEntryControllerPatientAPI.class);
        ReflectionTestUtils.setField(PatientAppAPIs.class, "coachJournalEntryControllerPatientAPI", api);
        when(api.listAll2(patientId)).thenReturn(Flux.fromIterable(expected));
        try (MockedStatic<SecurityUtil> security = Mockito.mockStatic(SecurityUtil.class)) {
            List<CoachGetAllJournalEntriesDTOPatientAPI> actual = journalEntryService.listAllJournalEntries(patientId, therapistId);
            assertEquals(expected, actual);
            security.verify(() -> SecurityUtil.checkOwnership(patient, therapistId));
        }
        verify(patientRepository).getReferenceById(patientId);
        verify(api).listAll2(patientId);
    }

    @Test
    void getOneJournalEntry() {
        String patientId = "p1";
        String journalEntryId = "j1";
        String therapistId = "t1";
        when(patientRepository.getReferenceById(patientId)).thenReturn(patient);
        CoachJournalEntryOutputDTOPatientAPI expected = new CoachJournalEntryOutputDTOPatientAPI();
        CoachJournalEntryControllerPatientAPI api = mock(CoachJournalEntryControllerPatientAPI.class);
        ReflectionTestUtils.setField(PatientAppAPIs.class, "coachJournalEntryControllerPatientAPI", api);
        when(api.getOne1(patientId, journalEntryId)).thenReturn(Mono.just(expected));
        try (MockedStatic<SecurityUtil> security = Mockito.mockStatic(SecurityUtil.class)) {
            CoachJournalEntryOutputDTOPatientAPI actual = journalEntryService.getOneJournalEntry(patientId, journalEntryId, therapistId);
            assertEquals(expected, actual);
            security.verify(() -> SecurityUtil.checkOwnership(patient, therapistId));
        }
        verify(patientRepository).getReferenceById(patientId);
        verify(api).getOne1(patientId, journalEntryId);
    }
}
