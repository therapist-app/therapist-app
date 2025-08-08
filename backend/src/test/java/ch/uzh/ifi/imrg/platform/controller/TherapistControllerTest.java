package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.LoginTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateTherapistDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistOutputDTO;
import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TherapistControllerTest {

    TherapistService svc = mock(TherapistService.class);
    TherapistController ctrl = new TherapistController(svc);

    TherapistOutputDTO stub() {
        TherapistOutputDTO d = new TherapistOutputDTO();
        d.setEmail("e");
        return d;
    }

    @Test
    void delegatesCorrectly() {
        TherapistOutputDTO dto = stub();

        when(svc.getTherapistById("tid")).thenReturn(dto);
        assertEquals(dto, ctrl.getCurrentlyLoggedInTherapist("tid"));
        verify(svc).getTherapistById("tid");

        when(svc.registerTherapist(any(), any(), any())).thenReturn(dto);
        assertEquals(dto,
                ctrl.createTherapist(new CreateTherapistDTO(),
                        mock(HttpServletRequest.class),
                        mock(HttpServletResponse.class)));
        verify(svc).registerTherapist(any(), any(), any());

        when(svc.updateTherapist(any(), eq("tid"))).thenReturn(dto);
        assertEquals(dto, ctrl.updateTherapist(new UpdateTherapistDTO(), "tid"));
        verify(svc).updateTherapist(any(), eq("tid"));

        when(svc.loginTherapist(any(), any(), any())).thenReturn(dto);
        assertEquals(dto,
                ctrl.loginTherapist(new LoginTherapistDTO(),
                        mock(HttpServletRequest.class),
                        mock(HttpServletResponse.class)));
        verify(svc).loginTherapist(any(), any(), any());

        ctrl.logoutTherapist(mock(HttpServletResponse.class));
        verify(svc).logoutTherapist(any());
    }
}
