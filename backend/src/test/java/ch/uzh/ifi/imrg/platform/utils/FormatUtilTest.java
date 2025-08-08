package ch.uzh.ifi.imrg.platform.utils;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FormatUtilTest {

    @Test
    void formatDate_and_indentBlock() {
        assertEquals("", FormatUtil.formatDate(null));
        String s = FormatUtil.formatDate(Instant.now());
        assertFalse(s.isBlank());

        assertEquals("", FormatUtil.indentBlock(null, 1, false));
        assertEquals("", FormatUtil.indentBlock("   ", 2, true));

        String ind1 = FormatUtil.indentBlock("x\ny", 1, false);
        assertTrue(ind1.startsWith("    "));
        String ind2 = FormatUtil.indentBlock("x\ny", 2, true);
        assertTrue(ind2.startsWith("          "));
    }

    @Test
    void appendDetail_coversAllBranches() {
        StringBuilder sb = new StringBuilder();

        int len = sb.length();
        FormatUtil.appendDetail(sb, "A", null);
        assertEquals(len, sb.length());

        len = sb.length();
        FormatUtil.appendDetail(sb, "B", "");
        assertEquals(len, sb.length());

        len = sb.length();
        FormatUtil.appendDetail(sb, "C", 0);
        assertEquals(len, sb.length());

        len = sb.length();
        FormatUtil.appendDetail(sb, "D", List.of());
        assertEquals(len, sb.length());

        len = sb.length();
        FormatUtil.appendDetail(sb, "E", Map.of());
        assertEquals(len, sb.length());

        FormatUtil.appendDetail(sb, "S", "str");
        assertTrue(sb.toString().contains("- **S:** str"));

        FormatUtil.appendDetail(sb, "N", 3.14);
        assertTrue(sb.toString().contains("- **N:** 3.14"));

        FormatUtil.appendDetail(sb, "C2", List.of("x"));
        assertTrue(sb.toString().contains("- **C2:** [x]"));

        FormatUtil.appendDetail(sb, "M2", Map.of("k","v"));
        assertTrue(sb.toString().contains("- **M2:** {k=v}"));

        Instant futureSeconds = Instant.now().plusSeconds(5);
        StringBuilder sb1 = new StringBuilder();
        FormatUtil.appendDetail(sb1, "F", futureSeconds);
        String t1 = sb1.toString();
        assertTrue(t1.contains("- **F:**"));
        assertTrue(t1.contains("in a few moments") || t1.contains("minute"));

        Instant pastSeconds = Instant.now().minusSeconds(5);
        StringBuilder sb2 = new StringBuilder();
        FormatUtil.appendDetail(sb2, "P", pastSeconds);
        String t2 = sb2.toString();
        assertTrue(t2.contains("- **P:**"));
        assertTrue(t2.contains("just now") || t2.contains("minute"));

        Instant plusMinutes = Instant.now().plusSeconds(5 * 60);
        StringBuilder sb3 = new StringBuilder();
        FormatUtil.appendDetail(sb3, "Min", plusMinutes);
        assertTrue(sb3.toString().contains("minute"));

        Instant plusHours = Instant.now().plusSeconds(3 * 3600);
        StringBuilder sb4 = new StringBuilder();
        FormatUtil.appendDetail(sb4, "Hr", plusHours);
        assertTrue(sb4.toString().contains("hour"));

        Instant plusDays = Instant.now().plus(Duration.ofDays(1)).plusSeconds(120);
        StringBuilder sb5 = new StringBuilder();
        FormatUtil.appendDetail(sb5, "Day", plusDays);
        assertTrue(sb5.toString().contains("day"));

        Instant plusWeeks = Instant.now().plusSeconds(14L * 24 * 3600);
        StringBuilder sb6 = new StringBuilder();
        FormatUtil.appendDetail(sb6, "Wk", plusWeeks);
        assertTrue(sb6.toString().contains("week"));

        Instant plusMonths = Instant.now().plusSeconds(60L * 24 * 3600);
        StringBuilder sb7 = new StringBuilder();
        FormatUtil.appendDetail(sb7, "Mo", plusMonths);
        assertTrue(sb7.toString().contains("month"));

        Object custom = new Object() { public String toString(){ return "X"; } };
        StringBuilder sb8 = new StringBuilder();
        FormatUtil.appendDetail(sb8, "O", custom);
        assertTrue(sb8.toString().contains("- **O:** X"));
    }
}
