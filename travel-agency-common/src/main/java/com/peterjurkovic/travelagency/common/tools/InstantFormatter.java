package com.peterjurkovic.travelagency.common.tools;

import java.text.ParseException;
import java.time.Instant;
import java.util.Locale;

import org.springframework.format.Formatter;

public class InstantFormatter implements Formatter<Instant> {

    @Override
    public String print(Instant object, Locale locale) {
        return DateUtils.formatDate(object);
    }

    @Override
    public Instant parse(String text, Locale locale) throws ParseException {
        return DateUtils.parseDateInstant(text);
    }

}
