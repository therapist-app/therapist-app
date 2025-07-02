package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.entity.Meeting;
import ch.uzh.ifi.imrg.platform.entity.MeetingNote;
import ch.uzh.ifi.imrg.platform.entity.Patient;
import ch.uzh.ifi.imrg.platform.entity.PatientDocument;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.entity.TherapistDocument;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LLMContextUtil {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());

  public static String getCounselingPlanContext(CounselingPlan counselingPlan) {
    StringBuilder promptBuilder = new StringBuilder();

    promptBuilder.append("\n\n");

    // --- General Context ---
    promptBuilder
        .append("# Counseling Plan Context\n")
        .append("\n\n")
        .append("## Plan Overview\n")
        .append("- **Plan ID:** ")
        .append(counselingPlan.getId())
        .append("\n")
        .append("- **Patient ID:** ")
        .append(counselingPlan.getPatient().getId())
        .append("\n")
        .append("- **Current Date and Time:** ")
        .append(FORMATTER.format(java.time.Instant.now()))
        .append("\n\n");

    // --- Phases ---
    List<CounselingPlanPhase> sortedPhases =
        counselingPlan.getCounselingPlanPhases().stream()
            .sorted(
                Comparator.comparing(
                    CounselingPlanPhase::getStartDate,
                    Comparator.nullsLast(Comparator.naturalOrder())))
            .collect(Collectors.toList());

    if (!sortedPhases.isEmpty()) {
      promptBuilder.append("## Counseling Plan Phases\n");
      for (int i = 0; i < sortedPhases.size(); i++) {
        CounselingPlanPhase phase = sortedPhases.get(i);
        promptBuilder
            .append("### Phase ")
            .append(i + 1)
            .append(": ")
            .append(phase.getPhaseName())
            .append("\n")
            .append("- **Start Date:** ")
            .append(phase.getStartDate() != null ? phase.getStartDate() : "Not set")
            .append("\n")
            .append("- **End Date:** ")
            .append(phase.getEndDate() != null ? phase.getEndDate() : "Not set")
            .append("\n\n");

        // --- Goals ---
        if (!phase.getPhaseGoals().isEmpty()) {
          promptBuilder.append("#### Goals for this Phase:\n");
          for (CounselingPlanPhaseGoal goal : phase.getPhaseGoals()) {
            promptBuilder
                .append("- **Goal:** ")
                .append(goal.getGoalName())
                .append("\n")
                .append("  - **Description:** ")
                .append(goal.getGoalDescription())
                .append("\n");
          }
          promptBuilder.append("\n");
        }

        // --- Exercises ---
        if (!phase.getPhaseExercises().isEmpty()) {
          promptBuilder.append("#### Exercises in this Phase:\n");
          for (Exercise exercise : phase.getPhaseExercises()) {
            promptBuilder
                .append("- **Exercise:** ")
                .append(exercise.getTitle())
                .append(" (ID: ")
                .append(exercise.getId())
                .append(")\n")
                .append("  - **Type:** ")
                .append(exercise.getExerciseType())
                .append("\n")
                .append("  - **Scheduled Start:** ")
                .append(
                    exercise.getExerciseStart() != null ? exercise.getExerciseStart() : "Not set")
                .append("\n")
                .append("  - **Scheduled End:** ")
                .append(exercise.getExerciseEnd() != null ? exercise.getExerciseEnd() : "Not set")
                .append("\n");

            // --- Exercise Components ---
            List<ExerciseComponent> components = exercise.getExerciseComponents();
            components.sort(Comparator.comparing(ExerciseComponent::getOrderNumber));

            if (!components.isEmpty()) {
              promptBuilder.append("  - **Components:**\n");
              for (ExerciseComponent component : components) {
                promptBuilder.append("    - ").append(component.getDescription()).append("\n");
              }
            }
          }
          promptBuilder.append("\n");
        }
      }
    } else {
      promptBuilder.append("This counseling plan currently has no phases defined.\n");
    }

    return promptBuilder.toString();
  }

  public static String getCoachAndClientContext(
      Therapist loggedInTherapist, List<Patient> patients) {
    StringBuilder promptBuilder = new StringBuilder();

    promptBuilder.append("\n\n");
    promptBuilder.append("## General Context\n");
    promptBuilder
        .append("- **Current Date and Time:** ")
        .append(FORMATTER.format(java.time.Instant.now()))
        .append("\n\n");

    if (loggedInTherapist.getTherapistDocuments() != null
        && !loggedInTherapist.getTherapistDocuments().isEmpty()) {
      promptBuilder.append("## Coach's General Documents\n");
      promptBuilder.append(
          "The following documents provide general context. Use them to answer questions that are not specific to a single client.\n\n");
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

    promptBuilder.append("---\n# Client Information\n");
    if (patients == null || patients.isEmpty()) {
      promptBuilder.append("No client data has been provided.\n");
    } else {
      for (Patient patient : patients) {
        promptBuilder.append("\n---\n\n");
        promptBuilder.append("## Client: ").append(patient.getName()).append("\n\n");

        if (patient.getPatientDocuments() != null && !patient.getPatientDocuments().isEmpty()) {
          promptBuilder.append("### Client Documents\n");
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

        if (patient.getMeetings() != null && !patient.getMeetings().isEmpty()) {
          promptBuilder.append("### Meetings\n");
          for (Meeting meeting : patient.getMeetings()) {
            String startTime =
                meeting.getMeetingStart() != null
                    ? FORMATTER.format(meeting.getMeetingStart())
                    : "N/A";
            String endTime =
                meeting.getMeetingEnd() != null ? FORMATTER.format(meeting.getMeetingEnd()) : "N/A";
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

        if (patient.getExercises() != null && !patient.getExercises().isEmpty()) {
          promptBuilder.append("### Exercises\n");
          for (Exercise exercise : patient.getExercises()) {
            promptBuilder.append("- **Exercise:** ").append(exercise.getTitle()).append("\n");
            promptBuilder.append("  - **Type:** ").append(exercise.getExerciseType()).append("\n");
            String exerciseStart =
                exercise.getExerciseStart() != null
                    ? FORMATTER.format(exercise.getExerciseStart())
                    : "N/A";
            String exerciseEnd =
                exercise.getExerciseEnd() != null
                    ? FORMATTER.format(exercise.getExerciseEnd())
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
