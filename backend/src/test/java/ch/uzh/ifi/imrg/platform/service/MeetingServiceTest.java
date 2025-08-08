package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import ch.uzh.ifi.imrg.platform.enums.MeetingStatus;
import ch.uzh.ifi.imrg.platform.repository.MeetingRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateMeetingDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.input.UpdateMeetingDTO;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import ch.uzh.ifi.imrg.platform.utils.SecurityUtil;
import jakarta.persistence.EntityManager;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
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
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MeetingServiceTest {

    @Mock MeetingRepository meetingRepository;
    @Mock PatientRepository patientRepository;
    @Mock EntityManager entityManager;
    @Mock Therapist therapist;

    MeetingService service;

    @BeforeEach
    void setUp() throws Exception {
        service = new MeetingService(meetingRepository, patientRepository);
        ReflectionTestUtils.setField(service, "entityManager", entityManager);

        Field apiField =
                PatientAppAPIs.class.getDeclaredField("coachMeetingControllerPatientAPI");
        Object apiMock = Mockito.mock(apiField.getType(), i -> Mono.empty());
        MethodHandles.Lookup lk = MethodHandles.privateLookupIn(PatientAppAPIs.class,
                MethodHandles.lookup());
        VarHandle vh =
                lk.findStaticVarHandle(PatientAppAPIs.class,
                        "coachMeetingControllerPatientAPI", apiField.getType());
        vh.set(apiMock);

        when(therapist.getId()).thenReturn("therapist1");
        when(therapist.getLlmModel()).thenReturn(LLMModel.AZURE_OPENAI);
    }

    @Test
    void createMeetingValid() {
        Patient patient = mock(Patient.class);
        when(patient.getTherapist()).thenReturn(therapist);
        when(patientRepository.getPatientById("p")).thenReturn(patient);
        when(meetingRepository.save(any(Meeting.class))).thenAnswer(i -> i.getArgument(0));

        Instant start = Instant.parse("2025-08-07T10:00:00Z");
        Instant end   = Instant.parse("2025-08-07T11:00:00Z");

        CreateMeetingDTO dto = mock(CreateMeetingDTO.class);
        when(dto.getMeetingStart()).thenReturn(start);
        when(dto.getMeetingEnd()).thenReturn(end);
        when(dto.getPatientId()).thenReturn("p");
        when(dto.getLocation()).thenReturn("loc");

        try (MockedStatic<SecurityUtil> ignored = Mockito.mockStatic(SecurityUtil.class)) {
            Meeting m = service.createMeeting(dto, "therapist1");
            assertEquals(start, m.getMeetingStart());
            assertEquals(end,   m.getMeetingEnd());
            assertEquals("loc", m.getLocation());
            assertEquals(MeetingStatus.CONFIRMED, m.getMeetingStatus());
        }
    }

    @Test
    void createMeetingInvalid() {
        Instant start = Instant.parse("2025-08-07T10:00:00Z");
        Instant end   = Instant.parse("2025-08-07T09:00:00Z");

        CreateMeetingDTO dto = mock(CreateMeetingDTO.class);
        when(dto.getMeetingStart()).thenReturn(start);
        when(dto.getMeetingEnd()).thenReturn(end);

        try (MockedStatic<SecurityUtil> ignored = Mockito.mockStatic(SecurityUtil.class)) {
            assertThrows(IllegalArgumentException.class,
                    () -> service.createMeeting(dto, "t"));
        }
    }

    @Test
    void updateMeetingAllFields() {
        Patient patient = mock(Patient.class);
        when(patient.getId()).thenReturn("p");
        when(patient.getTherapist()).thenReturn(therapist);

        Meeting meeting = new Meeting();
        meeting.setPatient(patient);
        Instant s  = Instant.parse("2025-08-07T10:00:00Z");
        Instant e  = Instant.parse("2025-08-07T11:00:00Z");
        meeting.setMeetingStart(s);
        meeting.setMeetingEnd(e);

        when(meetingRepository.getReferenceById(meeting.getId())).thenReturn(meeting);
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);

        UpdateMeetingDTO dto = mock(UpdateMeetingDTO.class);
        Instant ns = s.plusSeconds(3600);
        Instant ne = e.plusSeconds(3600);
        when(dto.getId()).thenReturn(meeting.getId());
        when(dto.getMeetingStart()).thenReturn(ns);
        when(dto.getMeetingEnd()).thenReturn(ne);
        when(dto.getLocation()).thenReturn("newLoc");
        when(dto.getMeetingStatus()).thenReturn(MeetingStatus.CONFIRMED);

        try (MockedStatic<SecurityUtil> ignored = Mockito.mockStatic(SecurityUtil.class)) {
            service.updateMeeting(dto, "therapist1");
            assertEquals(ns, meeting.getMeetingStart());
            assertEquals(ne, meeting.getMeetingEnd());
            assertEquals("newLoc", meeting.getLocation());
        }
    }

    @Test
    void updateMeetingNoChanges() {
        Patient patient = mock(Patient.class);
        when(patient.getTherapist()).thenReturn(therapist);

        Meeting meeting = new Meeting();
        meeting.setPatient(patient);
        Instant s = Instant.parse("2025-08-07T10:00:00Z");
        Instant e = Instant.parse("2025-08-07T11:00:00Z");
        meeting.setMeetingStart(s);
        meeting.setMeetingEnd(e);

        when(meetingRepository.getReferenceById(meeting.getId())).thenReturn(meeting);

        UpdateMeetingDTO dto = mock(UpdateMeetingDTO.class);
        when(dto.getId()).thenReturn(meeting.getId());

        try (MockedStatic<SecurityUtil> ignored = Mockito.mockStatic(SecurityUtil.class)) {
            service.updateMeeting(dto, "therapist1");
            assertEquals(s, meeting.getMeetingStart());
            assertEquals(e, meeting.getMeetingEnd());
        }
    }

    @Test
    void updateMeetingInvalidTime() {
        Patient patient = mock(Patient.class);
        when(patient.getTherapist()).thenReturn(therapist);

        Meeting meeting = new Meeting();
        meeting.setPatient(patient);
        Instant s = Instant.parse("2025-08-07T10:00:00Z");
        Instant e = Instant.parse("2025-08-07T11:00:00Z");
        meeting.setMeetingStart(s);
        meeting.setMeetingEnd(e);

        when(meetingRepository.getReferenceById(meeting.getId())).thenReturn(meeting);

        UpdateMeetingDTO dto = mock(UpdateMeetingDTO.class);
        when(dto.getId()).thenReturn(meeting.getId());
        when(dto.getMeetingEnd()).thenReturn(s.minusSeconds(1));

        try (MockedStatic<SecurityUtil> ignored = Mockito.mockStatic(SecurityUtil.class)) {
            assertThrows(IllegalArgumentException.class,
                    () -> service.updateMeeting(dto, "therapist1"));
        }
    }

    @Test
    void getAndDeleteMeetingFlows() {
        Patient patient = mock(Patient.class);
        when(patient.getTherapist()).thenReturn(therapist);

        Meeting meeting = new Meeting();
        meeting.setPatient(patient);
        List<Meeting> list = new ArrayList<>();
        list.add(meeting);

        when(patient.getMeetings()).thenReturn(list);
        when(meetingRepository.getReferenceById(meeting.getId())).thenReturn(meeting);
        when(patientRepository.getReferenceById("p")).thenReturn(patient);

        try (MockedStatic<SecurityUtil> ignored = Mockito.mockStatic(SecurityUtil.class)) {
            assertNotNull(service.getMeeting(meeting.getId(), "therapist1"));
            assertEquals(1, service.getAllMeetingsOfPatient("p", "therapist1").size());
            service.deleteMeetingById(meeting.getId(), "therapist1");
            assertTrue(list.isEmpty());
        }
    }
}
