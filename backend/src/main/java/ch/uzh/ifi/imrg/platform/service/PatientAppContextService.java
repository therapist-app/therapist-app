package ch.uzh.ifi.imrg.platform.service;

import ch.uzh.ifi.imrg.generated.model.*;
import ch.uzh.ifi.imrg.platform.constant.LogTypes;
import ch.uzh.ifi.imrg.platform.rest.dto.output.*;
import ch.uzh.ifi.imrg.platform.rest.mapper.PatientPsychologicalTestMapper;
import ch.uzh.ifi.imrg.platform.utils.PatientAppAPIs;
import java.lang.reflect.Method;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PatientAppContextService {

  private static final ZoneId ZONE = ZoneOffset.UTC;
  private static final Duration LOOKBACK = Duration.ofDays(30);

  public String buildContext(String patientId) {

    StringBuilder sb = new StringBuilder("\n\n## Client-App Context\n");

    sb.append(latestGad7(patientId));
    sb.append(exerciseCompletions(patientId));
    sb.append(chatbotSummary(patientId));
    sb.append(journalEntries(patientId));
    sb.append(logAggregation(patientId));

    System.out.println(sb.toString());

    return sb.toString();
  }

  private String latestGad7(String patientId) {
    try {
      List<PsychologicalTestOutputDTOPatientAPI> apiResults =
          PatientAppAPIs.coachPsychologicalTestControllerPatientAPI
              .getPsychologicalTestResults1(patientId, "GAD7")
              .collectList()
              .block();

      if (apiResults == null || apiResults.isEmpty()) return "";

      List<PsychologicalTestOutputDTO> results =
          apiResults.stream()
              .map(PatientPsychologicalTestMapper.INSTANCE::toPsychologicalTestOutputDTO)
              .filter(Objects::nonNull)
              .toList();

      if (results.isEmpty()) return "";

      PsychologicalTestOutputDTO latest =
          results.stream()
              .max(Comparator.comparing(PsychologicalTestOutputDTO::getCompletedAt))
              .orElseThrow();

      int totalScore =
          latest.getQuestions().stream()
              .mapToInt(
                  q -> {
                    for (String getter :
                        List.of(
                            "getSelectedAnswer", "getSelectedOption", "getAnswer", "getScore")) {
                      try {
                        Method m = q.getClass().getMethod(getter);
                        Object val = m.invoke(q);
                        if (val instanceof Integer i) return i;
                        if (val instanceof Number n) return n.intValue();
                      } catch (Exception ignored) {
                      }
                    }
                    return 0;
                  })
              .sum();

      return """
               ### Latest GAD-7
               • Completed: %s
               • Total score: %d

               """
          .formatted(latest.getCompletedAt().atZone(ZONE).toLocalDate(), totalScore);

    } catch (Exception e) {
      return "";
    }
  }

  private String exerciseCompletions(String patientId) {
    try {
      List<ExercisesOverviewOutputDTOPatientAPI> exercises =
          PatientAppAPIs.coachExerciseControllerPatientAPI
              .getAllExercises(patientId)
              .collectList()
              .blockOptional()
              .orElse(Collections.emptyList());

      Instant cutoff = Instant.now().minus(LOOKBACK);
      long completions = 0;

      for (ExercisesOverviewOutputDTOPatientAPI ex : exercises) {
        List<ExerciseInformationOutputDTOPatientAPI> infos =
            PatientAppAPIs.coachExerciseControllerPatientAPI
                .getExerciseInformation(patientId, ex.getId())
                .collectList()
                .blockOptional()
                .orElse(Collections.emptyList());

        completions +=
            infos.stream()
                .filter(i -> i.getEndTime() != null && i.getEndTime().isAfter(cutoff))
                .count();
      }

      return """
                    ### Exercise Completions (last 30 days)
                    • Total completed: %d

                    """
          .formatted(completions);

    } catch (Exception ignored) {
      return "";
    }
  }

  private String chatbotSummary(String patientId) {
    try {
      Instant end = Instant.now();
      Instant start = end.minus(LOOKBACK);

      GetConversationSummaryInputDTOPatientAPI dto = new GetConversationSummaryInputDTOPatientAPI();
      dto.setStart(start);
      dto.setEnd(end);

      ConversationSummaryOutputDTOPatientAPI summary =
          PatientAppAPIs.coachChatbotControllerPatientAPI
              .getConversationSummary(dto, patientId)
              .block();

      if (summary == null || summary.getConversationSummary() == null) return "";

      return """
                    ### Chatbot Summary (last 30 days)
                    %s

                    """
          .formatted(summary.getConversationSummary().trim());

    } catch (Exception ignored) {
      return "";
    }
  }

  private String journalEntries(String patientId) {
    try {
      List<CoachGetAllJournalEntriesDTOPatientAPI> entries =
          PatientAppAPIs.coachJournalEntryControllerPatientAPI
              .listAll2(patientId)
              .collectList()
              .blockOptional()
              .orElse(Collections.emptyList());

      Instant cutoff = Instant.now().minus(LOOKBACK);

      List<CoachGetAllJournalEntriesDTOPatientAPI> recent =
          entries.stream()
              .filter(e -> e.getCreatedAt() != null && e.getCreatedAt().isAfter(cutoff))
              .sorted()
              .limit(5)
              .toList();

      if (recent.isEmpty()) return "";

      StringBuilder sb = new StringBuilder("### Recent Journal Entries\n");
      recent.forEach(
          e ->
              sb.append("• ")
                  .append(e.getCreatedAt().atZone(ZONE).toLocalDate())
                  .append(" – ")
                  .append(e.getTitle())
                  .append("\n"));
      sb.append('\n');
      return sb.toString();

    } catch (Exception ignored) {
      return "";
    }
  }

  private String logAggregation(String patientId) {
    try {
      WeekFields wf = WeekFields.ISO;
      LocalDate cutoff = LocalDate.now(ZONE).minusWeeks(4);

      StringBuilder sb = new StringBuilder("### Log Counts (per ISO-week, last 4 weeks)\n");

      for (LogTypes type : LogTypes.values()) {
        List<LogOutputDTOPatientAPI> logs =
            PatientAppAPIs.coachLogControllerPatientAPI
                .listAll1(patientId, type.name())
                .collectList()
                .blockOptional()
                .orElse(Collections.emptyList());

        Map<Integer, Long> counts =
            logs.stream()
                .filter(
                    l ->
                        l.getTimestamp() != null
                            && l.getTimestamp().atZone(ZONE).toLocalDate().isAfter(cutoff))
                .collect(
                    Collectors.groupingBy(
                        l -> l.getTimestamp().atZone(ZONE).get(wf.weekOfWeekBasedYear()),
                        Collectors.counting()));

        if (!counts.isEmpty()) {
          sb.append("• ").append(type.name()).append(": ");
          counts.entrySet().stream()
              .sorted(Map.Entry.comparingByKey())
              .forEach(
                  e ->
                      sb.append("W")
                          .append(e.getKey())
                          .append('=')
                          .append(e.getValue())
                          .append(' '));
          sb.append('\n');
        }
      }

      sb.append('\n');
      return sb.toString();

    } catch (Exception ignored) {
      return "";
    }
  }
}
