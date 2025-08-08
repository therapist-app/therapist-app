package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientDocumentRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdatePatientDetailDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PatientServiceTest {

    @Mock PatientRepository patientRepo;
    @Mock TherapistRepository therapistRepo;
    @Mock PatientDocumentRepository docRepo;
    @Mock CounselingPlanRepository planRepo;

    PatientService service;

    @BeforeEach
    void init() {
        service = new PatientService(patientRepo, therapistRepo, docRepo, planRepo);
    }

    @Test
    void registerPatient_therapistNotFound() {
        when(therapistRepo.findById("tid")).thenReturn(Optional.empty());
        assertThrows(
                org.springframework.web.server.ResponseStatusException.class,
                () -> service.registerPatient(new CreatePatientDTO(), "tid"));
    }

    @Test
    void updatePatient_success() {
        Patient p = new Patient();
        p.setEmail("e@mail");
        when(patientRepo.getPatientById("pid")).thenReturn(p);
        UpdatePatientDetailDTO dto = new UpdatePatientDetailDTO();
        dto.setEmail("e@mail");

        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            PatientOutputDTO out = service.updatePatientDetails("pid", dto, "tid");
            assertEquals("e@mail", out.getEmail());
            verify(patientRepo).save(p);
        }
    }

    @Test
    void updatePatient_conflictEmail() {
        Patient p = new Patient();
        p.setEmail("mail1");
        when(patientRepo.getPatientById("pid")).thenReturn(p);
        UpdatePatientDetailDTO dto = new UpdatePatientDetailDTO();
        dto.setEmail("different");
        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            assertThrows(
                    org.springframework.web.server.ResponseStatusException.class,
                    () -> service.updatePatientDetails("pid", dto, "tid"));
        }
    }

    @Test
    void getAndDeleteFlows() {
        Patient p = new Patient();
        p.setEmail("e");
        Therapist t = new Therapist();
        List<Patient> list = new ArrayList<>();
        list.add(p);
        t.setPatients(list);
        p.setTherapist(t);

        when(patientRepo.getPatientById("pid")).thenReturn(p);
        when(therapistRepo.getReferenceById("tid")).thenReturn(t);

        try (MockedStatic<SecurityUtil> sec = Mockito.mockStatic(SecurityUtil.class)) {
            assertNotNull(service.getPatientById("pid", "tid"));
            assertEquals(1, service.getAllPatientsOfTherapist("tid").size());
            service.deletePatient("pid", "tid");
            assertTrue(list.isEmpty());
        }
    }
}
