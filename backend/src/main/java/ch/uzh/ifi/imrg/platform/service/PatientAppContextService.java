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
import java.util.function.BiFunction;
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

    return sb.toString();
  }

  private String latestGad7(String patientId) {

    BiFunction<String, String, List<PsychologicalTestOutputDTO>> fetch =
        (a, b) -> {
          try {
            List<PsychologicalTestOutputDTOPatientAPI> raw =
                PatientAppAPIs.coachPsychologicalTestControllerPatientAPI
                    .getPsychologicalTestResults1(a, b)
                    .collectList()
                    .block();
            if (raw == null) return List.of();
            return raw.stream()
                .map(PatientPsychologicalTestMapper.INSTANCE::toPsychologicalTestOutputDTO)
                .filter(Objects::nonNull)
                .toList();
          } catch (Exception e) {
            return List.of();
          }
        };

    List<String> names = List.of("GAD7", "Gad7", "GAD-7", "Gad-7");
    List<PsychologicalTestOutputDTO> results = List.of();

    outer:
    for (String n : names) {
      for (boolean reversed : List.of(false, true)) {
        results = reversed ? fetch.apply(n, patientId) : fetch.apply(patientId, n);
        if (!results.isEmpty()) break outer;
      }
    }

    if (results.isEmpty()) return "";

    PsychologicalTestOutputDTO latest =
        results.stream()
            .max(Comparator.comparing(PsychologicalTestOutputDTO::getCompletedAt))
            .orElseThrow();

    int totalScore =
        latest.getQuestions().stream()
            .mapToInt(
                q -> {
                  for (String g :
                      List.of("getSelectedAnswer", "getSelectedOption", "getAnswer", "getScore")) {
                    try {
                      Method m = q.getClass().getMethod(g);
                      Object v = m.invoke(q);
                      if (v instanceof Integer i) return i;
                      if (v instanceof Number n) return n.intValue();
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
      ZoneId zone = ZONE;
      WeekFields wf = WeekFields.ISO;
      LocalDate fourWeeksAgo = LocalDate.now(zone).minusWeeks(4);

      Map<LogTypes, List<LogOutputDTOPatientAPI>> rawByType = new EnumMap<>(LogTypes.class);
      for (LogTypes type : LogTypes.values()) {
        List<LogOutputDTOPatientAPI> lst =
            PatientAppAPIs.coachLogControllerPatientAPI
                .listAll1(patientId, type.name())
                .collectList()
                .blockOptional()
                .orElse(Collections.emptyList());
        rawByType.put(type, lst);
      }

      Map<String, EnumMap<LogTypes, Long>> weekMatrix = new TreeMap<>();

      rawByType.forEach(
          (type, logs) ->
              logs.stream()
                  .filter(
                      l ->
                          l.getTimestamp() != null
                              && l.getTimestamp().atZone(zone).toLocalDate().isAfter(fourWeeksAgo))
                  .forEach(
                      l -> {
                        LocalDate d = l.getTimestamp().atZone(zone).toLocalDate();
                        String yearWeek =
                            d.get(wf.weekBasedYear())
                                + "-W"
                                + String.format("%02d", d.get(wf.weekOfWeekBasedYear()));
                        weekMatrix
                            .computeIfAbsent(yearWeek, y -> new EnumMap<>(LogTypes.class))
                            .merge(type, 1L, Long::sum);
                      }));

      if (weekMatrix.isEmpty()) return "";

      StringBuilder sb = new StringBuilder("### Log Counts per Week (last 4 weeks)\n");
      weekMatrix.forEach(
          (yw, counts) -> {
            sb.append("• ").append(yw).append(": ");
            Arrays.stream(LogTypes.values())
                .forEach(
                    t -> {
                      long c = counts.getOrDefault(t, 0L);
                      if (c > 0) sb.append(t.name()).append('=').append(c).append(' ');
                    });
            sb.append('\n');
          });
      sb.append('\n');
      return sb.toString();

    } catch (Exception ignored) {
      return "";
    }
  }
}
