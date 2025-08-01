package ch.uzh.ifi.imrg.platform.utils;

import java.util.Date;

public final class ValidationUtil {

  private ValidationUtil() {}

  public static void assertStartBeforeEnd(Date start, Date end) {
    if (start != null && end != null && !end.after(start)) {
      throw new IllegalArgumentException("exercise.end_must_be_after_start");
    }
  }
}
