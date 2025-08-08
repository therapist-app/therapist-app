package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.service.MeetingNoteService;
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
class MeetingNoteControllerTest {

    @Mock
    MeetingNoteService service;

    MeetingNoteController controller;

    @BeforeEach
    void init() {
        controller = new MeetingNoteController(service);
    }

    @Test
    void createMeetingNote_delegates() {
        MeetingNoteOutputDTO out = new MeetingNoteOutputDTO("i", null, null, "t", "c");
        when(service.createMeetingNote(any(CreateMeetingNoteDTO.class), eq("x"))).thenReturn(out);
        assertSame(out, controller.createMeetingNote(new CreateMeetingNoteDTO(), "x"));
    }

    @Test
    void getMeetingNote_delegates() {
        MeetingNoteOutputDTO out = new MeetingNoteOutputDTO("i", null, null, "t", "c");
        when(service.getMeetingNote("n", "x")).thenReturn(out);
        assertSame(out, controller.getMeetingNoteById("n", "x"));
    }

    @Test
    void updateMeetingNote_delegates() {
        MeetingNoteOutputDTO out = new MeetingNoteOutputDTO("i", null, null, "t", "c");
        when(service.updatemeetingNote(any(UpdateMeetingNoteDTO.class), eq("x"))).thenReturn(out);
        assertSame(out, controller.updateMeetingNote(new UpdateMeetingNoteDTO(), "x"));
    }

    @Test
    void deleteMeetingNote_delegates() {
        controller.deleteMeetingNoteById("n", "x");
        verify(service).deleteMeetingNoteById("n", "x");
    }
}
