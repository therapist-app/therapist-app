package ch.uzh.ifi.imrg.platform.controller;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteSummaryDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingOutputDTO;
import ch.uzh.ifi.imrg.platform.service.MeetingService;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MeetingControllerTest {

    @Mock
    MeetingService meetingService;

    MeetingController controller;

    @BeforeEach
    void init() {
        controller = new MeetingController(meetingService);
    }

    @Test
    void createMeeting_mapsAndReturnsDTO() {
        Meeting meeting = new Meeting();
        meeting.setMeetingStart(Instant.parse("2025-08-08T08:00:00Z"));
        meeting.setMeetingEnd(Instant.parse("2025-08-08T09:00:00Z"));
        meeting.setLocation("room 1");
        meeting.setMeetingStatus(MeetingStatus.CONFIRMED);

        when(meetingService.createMeeting(any(CreateMeetingDTO.class), eq("therapist")))
                .thenReturn(meeting);

        MeetingOutputDTO dto = controller.createMeeting(mock(CreateMeetingDTO.class), "therapist");

        assertEquals(meeting.getId(), dto.getId());
        assertEquals("room 1", dto.getLocation());
    }

    @Test
    void createMeetingNoteSummary_delegatesAndReturnsString() {
        when(meetingService.createMeetingNoteSummary(any(CreateMeetingNoteSummaryDTO.class), eq("t")))
                .thenReturn("summary");
        assertEquals("summary",
                controller.createMeetingNoteSummary(mock(CreateMeetingNoteSummaryDTO.class), "t"));
    }

    @Test
    void getMeetingById_returnsWhatServiceReturns() {
        MeetingOutputDTO expected = new MeetingOutputDTO();
        when(meetingService.getMeeting("m1", "t")).thenReturn(expected);
        assertSame(expected, controller.getMeetingById("m1", "t"));
    }

    @Test
    void getMeetingsOfPatient_returnsListFromService() {
        MeetingOutputDTO dto = new MeetingOutputDTO();
        when(meetingService.getAllMeetingsOfPatient("p1", "t")).thenReturn(List.of(dto));
        List<MeetingOutputDTO> result = controller.getMeetingsOfPatient("p1", "t");
        assertEquals(1, result.size());
        assertSame(dto, result.getFirst());
    }

    @Test
    void updateMeeting_delegatesToService() {
        MeetingOutputDTO expected = new MeetingOutputDTO();
        when(meetingService.updateMeeting(any(UpdateMeetingDTO.class), eq("t")))
                .thenReturn(expected);
        assertSame(expected, controller.updateMeeting(mock(UpdateMeetingDTO.class), "t"));
    }

    @Test
    void deleteMeetingById_invokesService() {
        controller.deleteMeetingById("m1", "t");
        verify(meetingService).deleteMeetingById("m1", "t");
    }
}
