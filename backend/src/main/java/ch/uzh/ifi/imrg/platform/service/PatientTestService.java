package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.GAD7Test;
import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.repository.GAD7Repository;
import ch.uzh.ifi.imrg.platform.repository.MeetingRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.CreateGAD7TestDTO;
import ch.uzh.ifi.imrg.platform.rest.dto.output.GAD7TestOutputDTO;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PatientTestService {

  private final GAD7Repository gad7Repository;
  private final PatientRepository patientRepository;
  private final MeetingRepository meetingRepository;

  public PatientTestService(
      GAD7Repository gad7Repository,
      PatientRepository patientRepository,
      MeetingRepository meetingRepository) {
    this.gad7Repository = gad7Repository;
    this.patientRepository = patientRepository;
    this.meetingRepository = meetingRepository;
  }

  public GAD7TestOutputDTO createTest(CreateGAD7TestDTO dto) {
    Patient patient = patientRepository.getPatientById(dto.getPatientId());
    if (patient == null) {
      throw new IllegalArgumentException("Patient not found with id: " + dto.getPatientId());
    }

    Meeting meeting =
        meetingRepository
            .findById(dto.getMeetingId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Meeting not found with id: " + dto.getMeetingId()));

    GAD7Test test = new GAD7Test();
    test.setPatient(patient);
    test.setMeeting(meeting);
    test.setQuestion1(dto.getQuestion1());
    test.setQuestion2(dto.getQuestion2());
    test.setQuestion3(dto.getQuestion3());
    test.setQuestion4(dto.getQuestion4());
    test.setQuestion5(dto.getQuestion5());
    test.setQuestion6(dto.getQuestion6());
    test.setQuestion7(dto.getQuestion7());

    GAD7Test savedTest = gad7Repository.save(test);

    GAD7TestOutputDTO outputDTO = new GAD7TestOutputDTO();
    outputDTO.setTestId(savedTest.getTestId());
    outputDTO.setPatientId(savedTest.getPatient().getId());
    outputDTO.setMeetingId(savedTest.getMeeting().getId());
    outputDTO.setCreationDate(savedTest.getCreationDate());
    outputDTO.setQuestion1(savedTest.getQuestion1());
    outputDTO.setQuestion2(savedTest.getQuestion2());
    outputDTO.setQuestion3(savedTest.getQuestion3());
    outputDTO.setQuestion4(savedTest.getQuestion4());
    outputDTO.setQuestion5(savedTest.getQuestion5());
    outputDTO.setQuestion6(savedTest.getQuestion6());
    outputDTO.setQuestion7(savedTest.getQuestion7());

    return outputDTO;
  }

  public List<GAD7TestOutputDTO> getTestsByPatient(String patientId) {
    List<GAD7Test> tests = gad7Repository.findByPatient_Id(patientId);
    return tests.stream()
        .map(
            test -> {
              GAD7TestOutputDTO dto = new GAD7TestOutputDTO();
              dto.setTestId(test.getTestId());
              dto.setPatientId(test.getPatient().getId());
              dto.setMeetingId(test.getMeeting().getId());
              dto.setCreationDate(test.getCreationDate());
              dto.setQuestion1(test.getQuestion1());
              dto.setQuestion2(test.getQuestion2());
              dto.setQuestion3(test.getQuestion3());
              dto.setQuestion4(test.getQuestion4());
              dto.setQuestion5(test.getQuestion5());
              dto.setQuestion6(test.getQuestion6());
              dto.setQuestion7(test.getQuestion7());
              return dto;
            })
        .collect(Collectors.toList());
  }

  public GAD7TestOutputDTO getTest(String testId) {
    GAD7Test test =
        gad7Repository
            .findById(testId)
            .orElseThrow(
                () -> new IllegalArgumentException("GAD7 test not found with id: " + testId));

    GAD7TestOutputDTO outputDTO = new GAD7TestOutputDTO();
    outputDTO.setTestId(test.getTestId());
    outputDTO.setPatientId(test.getPatient().getId());
    outputDTO.setMeetingId(test.getMeeting().getId());
    outputDTO.setCreationDate(test.getCreationDate());
    outputDTO.setQuestion1(test.getQuestion1());
    outputDTO.setQuestion2(test.getQuestion2());
    outputDTO.setQuestion3(test.getQuestion3());
    outputDTO.setQuestion4(test.getQuestion4());
    outputDTO.setQuestion5(test.getQuestion5());
    outputDTO.setQuestion6(test.getQuestion6());
    outputDTO.setQuestion7(test.getQuestion7());
    return outputDTO;
  }

  public List<GAD7TestOutputDTO> getTestsByMeeting(String meetingId) {
    // Validate that meeting exists
    meetingRepository
        .findById(meetingId)
        .orElseThrow(() -> new IllegalArgumentException("Meeting not found with id: " + meetingId));

    List<GAD7Test> tests = gad7Repository.findByMeeting_Id(meetingId);
    return tests.stream()
        .map(
            test -> {
              GAD7TestOutputDTO dto = new GAD7TestOutputDTO();
              dto.setTestId(test.getTestId());
              dto.setPatientId(test.getPatient().getId());
              dto.setMeetingId(test.getMeeting().getId());
              dto.setCreationDate(test.getCreationDate());
              dto.setQuestion1(test.getQuestion1());
              dto.setQuestion2(test.getQuestion2());
              dto.setQuestion3(test.getQuestion3());
              dto.setQuestion4(test.getQuestion4());
              dto.setQuestion5(test.getQuestion5());
              dto.setQuestion6(test.getQuestion6());
              dto.setQuestion7(test.getQuestion7());
              return dto;
            })
        .collect(Collectors.toList());
  }
}
