package com.peterjurkovic.travelagency.common.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.i18n.phonenumbers.NumberParseException;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.converters.Nullable;

@RunWith(JUnitParamsRunner.class)
public class PhoneUtilsTest {

    @Parameters({
        "123",
        "+123",
        " +123 ",
        " 123 ",
        " +421 904 938 419 ",
        "2787240508621337",
        "27872 405 086 21337",
        "+ 2787240508621337",
        " + 2787240508621337",
    })
    @Test
    public void testIsValidNumber_shouldBeValidNumber(String number){
        
        boolean isValid = PhoneUtils.isValidNumber(number);
        
        assertThat(isValid).isTrue();
    }
    
    @Parameters({
        "",
        "null",
        "a",
        "12 ",
        " aa 123 ",
        " asdf "
    })
    @Test
    public void testIsValidNumber_shouldBeINVALIDNumber(@Nullable String number){
        
        boolean isValid = PhoneUtils.isValidNumber(number);
        
        assertThat(isValid).isFalse();
    }
    
    
    @Parameters({
        " +421 904 938 419    | 421904938419   ",
        " 421 904 938 419     | 421904938419   ",
        " +3906123456         | 3906123456     ",
        " +4407756738685      | 447756738685  ",
        " 4407756738685       | 447756738685  ",
        
    })
    @Test
    public void testToInternationalFormat_shouldReturnFormattedNumber(String number, String expected) throws NumberParseException{
        
        String actual = PhoneUtils.toInternationalFormat(number);
        
        assertThat(actual).isEqualTo(expected);
    }
}
