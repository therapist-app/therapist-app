package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.platform.entity.*;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanPhaseOutputDTO;
import ch.uzh.ifi.imrg.platform.service.CounselingPlanPhaseService;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LLMContextUtil {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());

  // Helper method to avoid boilerplate null-checks
  private static void appendDetail(StringBuilder sb, String label, Object value) {
    if (value != null
        && !(value instanceof String && ((String) value).isBlank())
        && !(value instanceof Integer && (Integer) value == 0)) {
      sb.append("- **").append(label).append(":** ").append(value).append("\n");
    }
  }

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
                    CounselingPlanPhase::getPhaseNumber,
                    Comparator.nullsLast(Comparator.naturalOrder())))
            .collect(Collectors.toList());

    if (!sortedPhases.isEmpty()) {
      promptBuilder.append("## Counseling Plan Phases\n");
      for (int i = 0; i < sortedPhases.size(); i++) {
        CounselingPlanPhase phase = sortedPhases.get(i);
        CounselingPlanPhaseOutputDTO outputDTO =
            CounselingPlanPhaseService.getOutputDto(phase, counselingPlan);

        promptBuilder
            .append("### Phase ")
            .append(outputDTO.getPhaseNumber())
            .append(": ")
            .append(outputDTO.getPhaseName())
            .append("\n")
            .append("- **Start Date:** ")
            .append(outputDTO.getStartDate() != null ? outputDTO.getStartDate() : "Not set")
            .append("\n")
            .append("- **End Date:** ")
            .append(outputDTO.getEndDate() != null ? outputDTO.getEndDate() : "Not set")
            .append("\n")
            .append("- **Duration in Weeks:** ")
            .append(outputDTO.getDurationInWeeks())
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
                .append(exercise.getExerciseTitle())
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
                promptBuilder
                    .append("    - ")
                    .append(component.getExerciseComponentDescription())
                    .append("\n");
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
        promptBuilder
            .append("## Client: ")
            .append(patient.getName())
            .append(" (ID: ")
            .append(patient.getId())
            .append(")\n\n");

        // --- Patient Details ---
        promptBuilder.append("### Patient Details\n");
        appendDetail(promptBuilder, "Gender", patient.getGender());
        appendDetail(promptBuilder, "Age", patient.getAge());
        appendDetail(promptBuilder, "Email", patient.getEmail());
        appendDetail(promptBuilder, "Phone Number", patient.getPhoneNumber());
        appendDetail(promptBuilder, "Address", patient.getAddress());
        appendDetail(promptBuilder, "Marital Status", patient.getMaritalStatus());
        appendDetail(promptBuilder, "Occupation", patient.getOccupation());
        appendDetail(promptBuilder, "Education", patient.getEducation());
        appendDetail(promptBuilder, "Religion", patient.getReligion());
        appendDetail(promptBuilder, "Date of Admission", patient.getDateOfAdmission());
        promptBuilder.append("\n");

        // --- Clinical History ---
        promptBuilder.append("### Clinical History & Context\n");
        appendDetail(promptBuilder, "Current Treatment", patient.getTreatmentCurrent());
        appendDetail(promptBuilder, "Past Treatment", patient.getTreatmentPast());
        appendDetail(promptBuilder, "Past Medical History", patient.getPastMedical());
        appendDetail(promptBuilder, "Past Psychiatric History", patient.getPastPsych());
        appendDetail(promptBuilder, "Family Illness History", patient.getFamilyIllness());
        appendDetail(promptBuilder, "Family Social History", patient.getFamilySocial());
        promptBuilder.append("\n");

        // --- Personal History ---
        promptBuilder.append("### Personal History\n");
        appendDetail(promptBuilder, "Perinatal", patient.getPersonalPerinatal());
        appendDetail(promptBuilder, "Childhood", patient.getPersonalChildhood());
        appendDetail(promptBuilder, "Adolescence", patient.getPersonalAdolescence());
        appendDetail(promptBuilder, "Puberty", patient.getPersonalPuberty());
        appendDetail(promptBuilder, "Occupational History", patient.getPersonalOccupational());
        appendDetail(promptBuilder, "Marital History", patient.getPersonalMarital());
        appendDetail(promptBuilder, "Premorbid Personality", patient.getPersonalPremorbid());
        promptBuilder.append("\n");

        // --- Complaints ---
        if (patient.getComplaints() != null && !patient.getComplaints().isEmpty()) {
          promptBuilder.append("### Chief Complaints\n");
          for (Complaint complaint : patient.getComplaints()) {
            appendDetail(promptBuilder, "Main Complaint", complaint.getMainComplaint());
            appendDetail(promptBuilder, "Duration", complaint.getDuration());
            appendDetail(promptBuilder, "Onset", complaint.getOnset());
            appendDetail(promptBuilder, "Course", complaint.getCourse());
            appendDetail(
                promptBuilder, "Precipitating Factors", complaint.getPrecipitatingFactors());
            appendDetail(promptBuilder, "Suicidal Ideation", complaint.getSuicidalIdeation());
            appendDetail(promptBuilder, "Associated Disturbances", complaint.getDisturbances());
          }
          promptBuilder.append("\n");
        }

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
          patient.getMeetings().stream()
              .sorted(
                  Comparator.comparing(
                      Meeting::getMeetingStart, Comparator.nullsLast(Comparator.reverseOrder())))
              .forEach(
                  meeting -> {
                    String startTime =
                        meeting.getMeetingStart() != null
                            ? FORMATTER.format(meeting.getMeetingStart())
                            : "N/A";
                    String endTime =
                        meeting.getMeetingEnd() != null
                            ? FORMATTER.format(meeting.getMeetingEnd())
                            : "N/A";
                    promptBuilder
                        .append("- **Meeting Time:** From ")
                        .append(startTime)
                        .append(" to ")
                        .append(endTime)
                        .append("\n");

                    if (meeting.getMeetingNotes() != null && !meeting.getMeetingNotes().isEmpty()) {
                      promptBuilder.append("  **Meeting Notes:**\n");
                      for (MeetingNote note : meeting.getMeetingNotes()) {
                        promptBuilder
                            .append("    - **Title:** ")
                            .append(note.getTitle())
                            .append("\n");
                        promptBuilder
                            .append("      **Content:** ")
                            .append(note.getContent().trim())
                            .append("\n");
                      }
                    }
                  });
          promptBuilder.append("\n");
        }

        // --- GAD-7 Tests ---
        if (patient.getGAD7Tests() != null && !patient.getGAD7Tests().isEmpty()) {
          promptBuilder.append("  **GAD-7 Anxiety Tests:**\n");
          for (GAD7Test test : patient.getGAD7Tests()) {
            int totalScore =
                test.getQuestion1()
                    + test.getQuestion2()
                    + test.getQuestion3()
                    + test.getQuestion4()
                    + test.getQuestion5()
                    + test.getQuestion6()
                    + test.getQuestion7();

            String severity;
            if (totalScore <= 4) {
              severity = "Minimal anxiety";
            } else if (totalScore <= 9) {
              severity = "Mild anxiety";
            } else if (totalScore <= 14) {
              severity = "Moderate anxiety";
            } else {
              severity = "Severe anxiety";
            }

            promptBuilder
                .append("    - **Test Date:** ")
                .append(FORMATTER.format(test.getCreationDate()))
                .append("\n");
            promptBuilder
                .append("      - **Total Score:** ")
                .append(totalScore)
                .append(" (")
                .append(severity)
                .append(")\n");
            promptBuilder
                .append("      - **Scores (Q1-Q7):** [")
                .append(test.getQuestion1())
                .append(", ")
                .append(test.getQuestion2())
                .append(", ")
                .append(test.getQuestion3())
                .append(", ")
                .append(test.getQuestion4())
                .append(", ")
                .append(test.getQuestion5())
                .append(", ")
                .append(test.getQuestion6())
                .append(", ")
                .append(test.getQuestion7())
                .append("]\n");
          }
        }

        if (patient.getExercises() != null && !patient.getExercises().isEmpty()) {
          promptBuilder.append("### Exercises\n");
          for (Exercise exercise : patient.getExercises()) {
            promptBuilder
                .append("- **Exercise:** ")
                .append(exercise.getExerciseTitle())
                .append("\n");
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
                    .append(component.getExerciseComponentDescription())
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
