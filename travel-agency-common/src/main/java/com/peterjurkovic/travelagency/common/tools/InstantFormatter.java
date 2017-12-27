package com.peterjurkovic.travelagency.common.tools;

import java.text.ParseException;
import java.time.Instant;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

public class InstantFormatter implements Formatter<Instant> {

    @Override
    public String print(Instant object, Locale locale) {
        String formatted = DateUtils.formatDate(object);
        return formatted == null ? "" : formatted;
    }

    @Override
    public Instant parse(String text, Locale locale) throws ParseException {
        if(StringUtils.hasText(text)){
            return DateUtils.parseDateInstant(text);
        }
        return null;
    }

}
