package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.platform.entity.CounselingPlan;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhase;
import ch.uzh.ifi.imrg.platform.entity.CounselingPlanPhaseGoal;
import ch.uzh.ifi.imrg.platform.entity.Exercise;
import ch.uzh.ifi.imrg.platform.entity.ExerciseComponent;
import ch.uzh.ifi.imrg.platform.repository.CounselingPlanRepository;
import ch.uzh.ifi.imrg.platform.repository.PatientRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.output.CounselingPlanOutputDTO;
import ch.uzh.ifi.imrg.platform.rest.mapper.CounselingPlanMapper;
import jakarta.transaction.Transactional;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CounselingPlanService {

  private final CounselingPlanRepository counselingPlanRepository;
  private final PatientRepository patientRepository;


  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());

  public CounselingPlanService(
      CounselingPlanRepository counselingPlanRepository, PatientRepository patientRepository) {
    this.counselingPlanRepository = counselingPlanRepository;
    this.patientRepository = patientRepository;
  }

  public CounselingPlanOutputDTO getCounselingPlanByPatientId(String patientId) {
    CounselingPlan counselingPlan =
        patientRepository.getReferenceById(patientId).getCounselingPlan();
    return CounselingPlanMapper.INSTANCE.convertEntityToCounselingPlanOutputDTO(counselingPlan);
  }

  
  public static String buildSystemPrompt(CounselingPlan counselingPlan) {
    StringBuilder promptBuilder = new StringBuilder();

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
            .sorted(Comparator.comparing(CounselingPlanPhase::getStartDate, Comparator.nullsLast(Comparator.naturalOrder())))
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
                .append(exercise.getExerciseStart() != null ? exercise.getExerciseStart() : "Not set")
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
                    .append(component.getDescription())
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
}