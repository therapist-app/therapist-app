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
import ch.uzh.ifi.imrg.platform.rest.dto.input.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.TherapistChatbotOutputDTO;
import ch.uzh.ifi.imrg.platform.utils.ChatRole;
import ch.uzh.ifi.imrg.platform.utils.LLMUZH;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TherapistChatbotService {
  private final PatientRepository patientRepository;

  public TherapistChatbotService(
      @Qualifier("patientRepository") PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public TherapistChatbotOutputDTO chat(
      TherapistChatbotInputDTO therapistChatbotInputDTO, Therapist loggedInTherapist) {
    List<Patient> patients;

    if (therapistChatbotInputDTO.getPatientId() != null) {
      patients = new ArrayList<>();
      patients.add(patientRepository.getReferenceById(therapistChatbotInputDTO.getPatientId()));
    } else {
      patients = loggedInTherapist.getPatients();
    }

    String systemPrompt = buildSystemPrompt(loggedInTherapist, patients);
    List<ChatMessageDTO> chatMessages = new ArrayList<>();
    chatMessages.add(new ChatMessageDTO(ChatRole.SYSTEM, systemPrompt));
    chatMessages.addAll(therapistChatbotInputDTO.getChatMessages());

    System.out.println(chatMessages);

    String responseMessage = LLMUZH.callLLM(chatMessages);
    TherapistChatbotOutputDTO therapistChatbotOutputDTO = new TherapistChatbotOutputDTO();
    therapistChatbotOutputDTO.setContent(responseMessage);
    return therapistChatbotOutputDTO;
  }

  private String buildSystemPrompt(Therapist loggedInTherapist, List<Patient> patients) {
    StringBuilder promptBuilder = new StringBuilder();

    // 1. Define the AI's Persona and Core Directives clearly at the start.
    promptBuilder.append("# AI Assistant Directives\n\n");
    promptBuilder.append(
        "You are a specialized AI assistant for a therapist. Your primary goal is to help the therapist by providing precise and context-aware answers to their questions.\n\n");
    promptBuilder.append("## Core Rules:\n");
    promptBuilder.append("1.  **Analyze an entire query before answering.**\n");
    promptBuilder.append(
        "2.  **Base all answers strictly on the information provided below.** Do not invent or infer information that isn't present in the context.\n");
    promptBuilder.append(
        "3.  **If the answer is not in the provided documents or patient data, explicitly state that.** For example, say 'The answer could not be found in the provided context.'\n");
    promptBuilder.append(
        "4.  **Maintain patient confidentiality in your responses.** Refer to patients by name only as provided here.\n");
    promptBuilder.append(
        "5.  **Be concise and to the point.** Provide the information requested without unnecessary conversational filler.\n\n");

    // 2. Provide General Context, like date and time.
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());
    promptBuilder.append("--- \n");
    promptBuilder.append("## General Context\n");
    promptBuilder
        .append("- **Current Date and Time:** ")
        .append(formatter.format(java.time.Instant.now()))
        .append("\n\n");

    // 3. Structure the Therapist's context documents clearly.
    if (loggedInTherapist.getTherapistDocuments() != null
        && !loggedInTherapist.getTherapistDocuments().isEmpty()) {
      promptBuilder.append("## Therapist's General Documents\n");
      promptBuilder.append(
          "The following documents provide general context. Use them to answer questions that are not specific to a single patient.\n\n");
      for (TherapistDocument doc : loggedInTherapist.getTherapistDocuments()) {
        if (doc.getExtractedText() != null && !doc.getExtractedText().isBlank()) {
          promptBuilder
              .append("### Document: ")
              .append(doc.getFileName() != null ? doc.getFileName() : "Untitled")
              .append("\n");
          promptBuilder.append("```text\n");
          promptBuilder.append(doc.getExtractedText().trim()).append("\n");
          promptBuilder.append("```\n\n");
        }
      }
    }

    // 4. Structure all patient data under a main heading, with each patient clearly separated.
    promptBuilder.append("---\n# Patient Information\n");
    if (patients == null || patients.isEmpty()) {
      promptBuilder.append("No patient data has been provided.\n");
    } else {
      for (Patient patient : patients) {
        promptBuilder.append("\n---\n\n"); // Use a horizontal rule to clearly separate patients.
        promptBuilder.append("## Patient: ").append(patient.getName()).append("\n\n");

        // Patient Documents
        if (patient.getPatientDocuments() != null && !patient.getPatientDocuments().isEmpty()) {
          promptBuilder.append("### Patient Documents\n");
          for (PatientDocument doc : patient.getPatientDocuments()) {
            if (doc.getExtractedText() != null && !doc.getExtractedText().isBlank()) {
              promptBuilder.append("- **Document Title:** ").append(doc.getFileName()).append("\n");
              promptBuilder
                  .append("  **Content:**\n  ```text\n")
                  .append(doc.getExtractedText().trim())
                  .append("\n  ```\n");
            }
          }
          promptBuilder.append("\n");
        }

        // Patient Meetings
        if (patient.getMeetings() != null && !patient.getMeetings().isEmpty()) {
          promptBuilder.append("### Meetings\n");
          for (Meeting meeting : patient.getMeetings()) {
            String startTime =
                meeting.getMeetingStart() != null
                    ? formatter.format(meeting.getMeetingStart())
                    : "N/A";
            String endTime =
                meeting.getMeetingEnd() != null ? formatter.format(meeting.getMeetingEnd()) : "N/A";
            promptBuilder
                .append("- **Meeting Time:** From ")
                .append(startTime)
                .append(" to ")
                .append(endTime)
                .append("\n");

            if (meeting.getMeetingNotes() != null && !meeting.getMeetingNotes().isEmpty()) {
              promptBuilder.append("  **Meeting Notes:**\n");
              for (MeetingNote note : meeting.getMeetingNotes()) {
                promptBuilder.append("    - **Title:** ").append(note.getTitle()).append("\n");
                promptBuilder
                    .append("      **Content:** ")
                    .append(note.getContent().trim())
                    .append("\n");
              }
            }
          }
          promptBuilder.append("\n");
        }

        // Patient Exercises
        if (patient.getExercises() != null && !patient.getExercises().isEmpty()) {
          promptBuilder.append("### Exercises\n");
          for (Exercise exercise : patient.getExercises()) {
            promptBuilder.append("- **Exercise:** ").append(exercise.getTitle()).append("\n");
            promptBuilder.append("  - **Type:** ").append(exercise.getExerciseType()).append("\n");
            String exerciseStart =
                exercise.getExerciseStart() != null
                    ? formatter.format(exercise.getExerciseStart())
                    : "N/A";
            String exerciseEnd =
                exercise.getExerciseEnd() != null
                    ? formatter.format(exercise.getExerciseEnd())
                    : "Ongoing";
            promptBuilder
                .append("  - **Duration:** ")
                .append(exerciseStart)
                .append(" to ")
                .append(exerciseEnd)
                .append("\n");
            promptBuilder
                .append("  - **Status:** ")
                .append(exercise.getIsPaused() ? "Paused" : "Active")
                .append("\n");

            if (exercise.getExerciseComponents() != null
                && !exercise.getExerciseComponents().isEmpty()) {
              promptBuilder.append("  - **Components:**\n");
              for (ExerciseComponent component : exercise.getExerciseComponents()) {
                promptBuilder
                    .append("    - **Description:** ")
                    .append(component.getDescription())
                    .append("\n");
                promptBuilder
                    .append("      **Type:** ")
                    .append(component.getExerciseComponentType())
                    .append("\n");
              }
            }
          }
          promptBuilder.append("\n");
        }
      }
    }

    return promptBuilder.toString();
  }
}
