package com.peterjurkovic.travelagency.common.tools;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @author peter 
 */
public abstract class DateUtils {

    
    private final static String DATE_FORMAT = "yyyy-MM-dd";
    private final static String TIME_FORMAT = "HH:mm:ss";
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT + " " + TIME_FORMAT);
    
    public static String formatDate(TemporalAccessor dateTime) {
        if (dateTime == null) {
            return null;
        }
        return DATE_FORMATTER.format(dateTime);
    }

    public static String formatInstant(Instant instant) {
        if (instant == null) {
            return null;
        }

        return DATE_FORMATTER.withZone(ZoneOffset.UTC).format(instant);
    }

    public static LocalDateTime parseDate(String date) {
        if (date == null) {
            return null;
        }
        return parseDateTime(date + " 00:00:00");
    }
    
    public static LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    public static Instant parseDateInstant(String strDate) {
        LocalDateTime dateTime = parseDate(strDate);
        if (dateTime != null) {
            return dateTime.toInstant(ZoneOffset.UTC);
        }
        return null;
    }
}
