package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.TherapistChatbotInputDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatbotOutputDTO;
import ch.uzh.ifi.imrg.platform.service.TherapistChatbotService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TherapistChatbotControllerTest {

    @Test
    void controllerDelegatesToService() {
        TherapistChatbotService svc = mock(TherapistChatbotService.class);
        TherapistChatbotController ctrl = new TherapistChatbotController(svc);

        TherapistChatbotInputDTO in = new TherapistChatbotInputDTO();
        TherapistChatbotOutputDTO out = new TherapistChatbotOutputDTO();
        out.setContent("hi");

        when(svc.chat(in, "tid")).thenReturn(out);

        TherapistChatbotOutputDTO resp = ctrl.chatWithTherapistChatbot(in, "tid");
        assertEquals("hi", resp.getContent());
        verify(svc).chat(in, "tid");
    }
}
