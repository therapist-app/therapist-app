package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreatePatientDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdatePatientDetailDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.PatientOutputDTO;
import ch.uzh.ifi.imrg.platform.service.PatientService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PatientControllerTest {

    @Mock PatientService svc;
    PatientController ctrl;

    @BeforeEach
    void init() {
        ctrl = new PatientController(svc);
    }

    @Test
    void create_delegates() {
        PatientOutputDTO dto = new PatientOutputDTO();
        when(svc.registerPatient(any(CreatePatientDTO.class), eq("t"))).thenReturn(dto);
        assertSame(dto, ctrl.createPatientForTherapist(new CreatePatientDTO(), "t"));
    }

    @Test
    void getAll_delegates() {
        when(svc.getAllPatientsOfTherapist("t")).thenReturn(List.of(new PatientOutputDTO()));
        assertEquals(1, ctrl.getPatientsOfTherapist("t").size());
    }

    @Test
    void getById_delegates() {
        PatientOutputDTO dto = new PatientOutputDTO();
        when(svc.getPatientById("p", "t")).thenReturn(dto);
        assertSame(dto, ctrl.getPatientById("p", "t"));
    }

    @Test
    void update_delegates() {
        PatientOutputDTO dto = new PatientOutputDTO();
        when(svc.updatePatientDetails(eq("p"), any(UpdatePatientDetailDTO.class), eq("t")))
                .thenReturn(dto);
        assertSame(dto, ctrl.updatePatientDetails("p", new UpdatePatientDetailDTO(), "t"));
    }

    @Test
    void delete_delegates() {
        ctrl.deletePatient("p", "t");
        verify(svc).deletePatient("p", "t");
    }
}
