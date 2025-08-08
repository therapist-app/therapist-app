package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void addAmountOfWeeksWorks() {
        Instant start = Instant.parse("2020-01-15T10:00:00Z");
        Instant expected = start.plus(14, ChronoUnit.DAYS);
        assertEquals(expected, DateUtil.addAmountOfWeeks(start, 2));
    }
}
