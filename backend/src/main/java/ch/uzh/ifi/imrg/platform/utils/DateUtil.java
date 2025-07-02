package ch.uzh.ifi.imrg.platform.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class DateUtil {

  public static Instant addAmountOfWeeks(Instant startDate, long amountOfWeeks) {
    return startDate
        .atZone(ZoneId.systemDefault())
        .plus(amountOfWeeks, ChronoUnit.WEEKS)
        .toInstant();
  }
}
