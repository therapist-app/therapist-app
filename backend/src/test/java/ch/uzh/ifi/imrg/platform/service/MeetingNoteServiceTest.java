package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.MeetingNote;
import ch.uzh.ifi.imrg.platform.repository.MeetingNoteRepository;
import ch.uzh.ifi.imrg.platform.repository.MeetingRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingNoteDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.MeetingNoteOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import java.util.List;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MeetingNoteServiceTest {

    @Mock MeetingNoteRepository noteRepo;
    @Mock MeetingRepository meetingRepo;

    MeetingNoteService service;

    @BeforeEach
    void init() {
        service = new MeetingNoteService(noteRepo, meetingRepo);
    }

    @Test
    void createMeetingNote_flow() {
        Meeting meeting = new Meeting();
        when(meetingRepo.getReferenceById("m")).thenReturn(meeting);
        when(noteRepo.save(any(MeetingNote.class))).thenAnswer(i -> i.getArgument(0));
        CreateMeetingNoteDTO dto = new CreateMeetingNoteDTO();
        dto.setMeetingId("m");
        dto.setTitle("t");
        dto.setContent("c");
        try (MockedStatic<SecurityUtil> s = Mockito.mockStatic(SecurityUtil.class)) {
            MeetingNoteOutputDTO out = service.createMeetingNote(dto, "therapist");
            assertEquals("t", out.getTitle());
            assertEquals("c", out.getContent());
            verify(noteRepo).save(any(MeetingNote.class));
        }
    }

    @Test
    void getMeetingNote_flow() {
        MeetingNote note = new MeetingNote();
        note.setTitle("t");
        when(noteRepo.getReferenceById(note.getId())).thenReturn(note);
        try (MockedStatic<SecurityUtil> s = Mockito.mockStatic(SecurityUtil.class)) {
            MeetingNoteOutputDTO out = service.getMeetingNote(note.getId(), "t");
            assertEquals("t", out.getTitle());
        }
    }

    @Test
    void getAllNotes_flow() {
        Meeting meeting = new Meeting();
        MeetingNote n1 = new MeetingNote();
        MeetingNote n2 = new MeetingNote();
        meeting.getMeetingNotes().addAll(List.of(n1, n2));
        when(meetingRepo.getReferenceById("m")).thenReturn(meeting);
        try (MockedStatic<SecurityUtil> s = Mockito.mockStatic(SecurityUtil.class)) {
            List<MeetingNoteOutputDTO> list = service.getAllmeetingsNotesOfmeeting("m", "t");
            assertEquals(2, list.size());
        }
    }

    @Test
    void updateMeetingNote_updatesFields() {
        MeetingNote note = new MeetingNote();
        note.setTitle("old");
        note.setContent("oldc");
        when(noteRepo.getReferenceById(note.getId())).thenReturn(note);
        when(noteRepo.save(any(MeetingNote.class))).thenReturn(note);
        UpdateMeetingNoteDTO dto = new UpdateMeetingNoteDTO();
        dto.setId(note.getId());
        dto.setTitle("new");
        dto.setContent("newc");
        try (MockedStatic<SecurityUtil> s = Mockito.mockStatic(SecurityUtil.class)) {
            MeetingNoteOutputDTO out = service.updatemeetingNote(dto, "t");
            assertEquals("new", out.getTitle());
            assertEquals("newc", out.getContent());
        }
    }

    @Test
    void updateMeetingNote_noChanges() {
        MeetingNote note = new MeetingNote();
        note.setTitle("old");
        note.setContent("old");
        when(noteRepo.getReferenceById(note.getId())).thenReturn(note);
        UpdateMeetingNoteDTO dto = new UpdateMeetingNoteDTO();
        dto.setId(note.getId());
        try (MockedStatic<SecurityUtil> s = Mockito.mockStatic(SecurityUtil.class)) {
            MeetingNoteOutputDTO out = service.updatemeetingNote(dto, "t");
            assertEquals("old", out.getTitle());
            assertEquals("old", out.getContent());
        }
    }

    @Test
    void deleteMeetingNote_flow() {
        Meeting meeting = new Meeting();
        MeetingNote note = new MeetingNote();
        note.setMeeting(meeting);
        meeting.getMeetingNotes().add(note);
        when(noteRepo.getReferenceById(note.getId())).thenReturn(note);
        try (MockedStatic<SecurityUtil> s = Mockito.mockStatic(SecurityUtil.class)) {
            service.deleteMeetingNoteById(note.getId(), "t");
            assertTrue(meeting.getMeetingNotes().isEmpty());
        }
    }
}
