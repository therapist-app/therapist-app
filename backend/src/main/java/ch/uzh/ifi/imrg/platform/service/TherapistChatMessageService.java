package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.MeetingNote;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatCompletionOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TherapistChatMessageService {
  private final PatientRepository patientRepository;
  private final TherapistRepository therapistRepository;

  public TherapistChatMessageService(
      @Qualifier("patientRepository") PatientRepository patientRepository,
      @Qualifier("therapistRepository") TherapistRepository therapistRepository) {
    this.patientRepository = patientRepository;
    this.therapistRepository = therapistRepository;
  }

  public TherapistChatCompletionOutputDTO chat(
      TherapistChatCompletionDTO therapistChatCompletionDTO, Therapist loggedInTherapist) {
    List<Patient> patients;

    if (therapistChatCompletionDTO.getPatientId() != null) {
      patients = new ArrayList<>();
      patients.add(patientRepository.getReferenceById(therapistChatCompletionDTO.getPatientId()));
    } else {
      patients = loggedInTherapist.getPatients();
    }

    String systemPrompt = buildSystemPrompt(loggedInTherapist, patients);
    List<ChatMessageDTO> chatMessages = new ArrayList<>();
    chatMessages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    chatMessages.addAll(therapistChatCompletionDTO.getChatMessages());

    System.out.println(chatMessages);

    String responseMessage = LLMUZH.callLLM(chatMessages);
    TherapistChatCompletionOutputDTO therapistChatCompletionOutputDTO =
        new TherapistChatCompletionOutputDTO();
    therapistChatCompletionOutputDTO.setContent(responseMessage);
    return therapistChatCompletionOutputDTO;
  }

  private String buildSystemPrompt(Therapist loggedInTherapist, List<Patient> patients) {
    String systemPrompt = "You are a helpful assistant to this therapist.";
    systemPrompt += "Try to answer his questions precicely.";
    systemPrompt += "The current date and time is: " + java.time.Instant.now();
    systemPrompt += "\n\n";

    String therapistDocumentsString = "";
    for (TherapistDocument therapistDocument : loggedInTherapist.getTherapistDocuments()) {
      if (therapistDocument.getExtractedText() != null) {
        therapistDocumentsString += therapistDocument.getExtractedText();
        therapistDocumentsString += "\n\n";
      }
    }
    if (!therapistDocumentsString.equals("")) {
      systemPrompt += "Use the following documents as context:";
      systemPrompt += therapistDocumentsString;
    }

    String patientString = "";
    for (Patient patient : patients) {
      patientString += "Patient name: " + patient.getName() + "\n";

      String patientMeetingsString = "";
      for (Meeting meeting : patient.getMeetings()) {
        patientMeetingsString +=
            "Meeting from: "
                + meeting.getMeetingStart()
                + ", to: "
                + meeting.getMeetingEnd()
                + "\n";

        String meetingNotesString = "";
        for (MeetingNote meetingNote : meeting.getMeetingNotes()) {
          meetingNotesString += "Meeting note title: " + meetingNote.getTitle();
          meetingNotesString += "\nMeeting note content: " + meetingNote.getContent();
          meetingNotesString += "\n\n";
        }
        if (!meetingNotesString.equals("")) {
          patientMeetingsString += "The Meeting contains the following meeting notes:\n";
          patientMeetingsString += meetingNotesString;
        }
      }
      if (!patientMeetingsString.equals("")) {
        patientString += "The patient has scheduled the following meetings:\n";
        patientString += patientMeetingsString;
      }

      String exercisesString = "";
      for (Exercise exercise : patient.getExercises()) {
        exercisesString += "Exercise name: " + exercise.getTitle();
        exercisesString += "\nExercise type: " + exercise.getExerciseType();
        exercisesString += "\nExercise start date: " + exercise.getExerciseStart();
        exercisesString += "\nExercise end date: " + exercise.getExerciseEnd();
        exercisesString += "\nIs exercise currently paused: " + exercise.getIsPaused();

        String exerciseComponentsString = "";
        for (ExerciseComponent exerciseComponent : exercise.getExerciseComponents()) {

          exerciseComponentsString +=
              "\nExercise component description: " + exerciseComponent.getDescription();
          exerciseComponentsString +=
              "\nExercise component type: " + exerciseComponent.getExerciseComponentType();
          exerciseComponentsString += "\n\n";
        }
        if (!exerciseComponentsString.equals("")) {
          exercisesString += "\nExercise components:\n";
          exercisesString += exerciseComponentsString;
        }

        exercisesString += "\n\n";
      }
      if (!exercisesString.equals("")) {
        patientString += "The patient has the following exercises:\n";

        String patientDocumentsString = "";
        for (PatientDocument patientDocument : patient.getPatientDocuments()) {
          if (patientDocument.getExtractedText() != null
              && !patientDocument.getExtractedText().equals("")) {
            patientDocumentsString += "Patient document title: " + patientDocument.getFileName();
            patientDocumentsString +=
                "\nPatient document content: " + patientDocument.getExtractedText();
            patientDocumentsString += "\n\n";
          }
        }
        if (!patientDocumentsString.equals("")) {
          patientString += "The patient has the following documents:\n";
          patientString += patientDocumentsString;
        }
      }

      if (!patientString.equals("")) {
        systemPrompt += "Patient information:\n";
        systemPrompt += patientString;
      }
    }

    return systemPrompt;
  }
}
