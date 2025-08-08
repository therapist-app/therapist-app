package ch.uzh.ifi.imrg.platform.entity;

import ch.uzh.ifi.imrg.platform.LLM.LLMContextBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeetingNoteTest {

    @Test
    void owningTherapistId_resolvesThroughGraph() {
        MeetingNote note = new MeetingNote();
        Meeting meeting = mock(Meeting.class);
        Patient patient = mock(Patient.class);
        Therapist therapist = mock(Therapist.class);
        when(meeting.getPatient()).thenReturn(patient);
        when(patient.getTherapist()).thenReturn(therapist);
        when(therapist.getId()).thenReturn("tid");
        note.setMeeting(meeting);
        assertEquals("tid", note.getOwningTherapistId());
    }

    @Test
    void toLLMContext_delegatesToBuilder() {
        MeetingNote note = new MeetingNote();
        try (MockedStatic<LLMContextBuilder> st = Mockito.mockStatic(LLMContextBuilder.class)) {
            st.when(() -> LLMContextBuilder.getOwnProperties(note, 1))
                    .thenReturn(new StringBuilder("ctx"));
            assertEquals("ctx", note.toLLMContext(1));
        }
    }
}
