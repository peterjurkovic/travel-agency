package com.peterjurkovic.travelagency.common.tools;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void testFormatInstant() {
        Instant instant = of(2017, 12, 10);

        String formatted = DateUtils.formatInstant(instant);

        assertThat(formatted).isEqualTo("2017-12-10");
    }

    @Test
    public void testParseInstant() {
        String formatted = "2017-12-10";

        Instant instant = DateUtils.parseDateInstant(formatted);

        assertThat(instant).isEqualTo(of(2017, 12, 10));
    }

    static Instant of(int year, int month, int dayOfMonth) {
        return LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0).toInstant(ZoneOffset.UTC);
    }
}
