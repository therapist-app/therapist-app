package ch.uzh.ifi.imrg.platform.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

public class FormatUtil {
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());

  public static String formatDate(Instant date) {
    if (date == null) return "";
    return FORMATTER.format(date);
  }

  public static String indentBlock(String textBlock, Integer level, Boolean rightAfterLevel) {
    if (textBlock == null || textBlock.isBlank()) {
      return "";
    }
    level *= 4;
    if (rightAfterLevel) {
      level += 2;
    }
    return textBlock.indent(level);
  }

  private static String getRelativeTimeString(Instant instant) {
    if (instant == null) return "";

    Duration duration = Duration.between(Instant.now(), instant);
    boolean isFuture = !duration.isNegative();
    duration = duration.abs();

    long days = duration.toDays();
    if (days > 0) {
      if (days >= 30) {
        long months = days / 30;
        return String.format(
            "(~%d month%s %s)", months, months > 1 ? "s" : "", isFuture ? "from now" : "ago");
      }
      if (days >= 7) {
        long weeks = days / 7;
        return String.format(
            "(~%d week%s %s)", weeks, weeks > 1 ? "s" : "", isFuture ? "from now" : "ago");
      }
      return String.format(
          "(%d day%s %s)", days, days > 1 ? "s" : "", isFuture ? "from now" : "ago");
    }

    long hours = duration.toHours();
    if (hours > 0) {
      return String.format(
          "(%d hour%s %s)", hours, hours > 1 ? "s" : "", isFuture ? "from now" : "ago");
    }

    long minutes = duration.toMinutes();
    if (minutes > 0) {
      return String.format(
          "(%d minute%s %s)", minutes, minutes > 1 ? "s" : "", isFuture ? "from now" : "ago");
    }

    return isFuture ? "(in a few moments)" : "(just now)";
  }

  public static void appendDetail(StringBuilder sb, String label, Object value) {
    if (isPresent(value)) {
      String valueString;
      if (value instanceof Instant instant) {
        String formattedDate = formatDate(instant);
        String relativeTime = getRelativeTimeString(instant);
        valueString = formattedDate + " " + relativeTime;
      } else {
        valueString = value.toString();
      }
      sb.append("- **").append(label).append(":** ").append(valueString).append("\n");
    }
  }

  private static boolean isPresent(Object value) {
    if (value == null) return false;
    if (value instanceof String s) return !s.isBlank();
    if (value instanceof Number n) return n.doubleValue() != 0.0;
    if (value instanceof Collection<?> c) return !c.isEmpty();
    if (value instanceof Map<?, ?> m) return !m.isEmpty();
    return true;
  }
}
