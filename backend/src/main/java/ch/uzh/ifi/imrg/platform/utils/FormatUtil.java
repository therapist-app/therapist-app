package ch.uzh.ifi.imrg.platform.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

public class FormatUtil {
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());

  public static String formatDate(Instant date) {
    return FORMATTER.format(date);
  }

  public static void appendDetail(StringBuilder sb, String label, Object value) {
    if (isPresent(value)) {
      sb.append("- **").append(label).append(":** ").append(value).append("\n");
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
