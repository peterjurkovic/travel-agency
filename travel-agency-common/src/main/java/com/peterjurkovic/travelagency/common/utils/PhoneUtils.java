package com.peterjurkovic.travelagency.common.utils;

import java.util.Optional;
import java.util.regex.Pattern;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.NumberParseException.ErrorType;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public abstract class PhoneUtils {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?\\d{3,16}$");

    private static final Pattern REMOVE_NON_NUMERIC_CHARS = Pattern.compile("[\\D]");
    
    public static boolean isValidNumber(String number) {
        if (number == null) {
            return false;
        }
        number = number.trim().replaceAll("\\s+", "");
        if (!PHONE_PATTERN.matcher(number).matches()) {
            return false;
        }
        return true;
    }

    public static String toInternationalFormat(String number) throws NumberParseException {
      
        if (!isValidNumber(number)) {
           throw new NumberParseException(ErrorType.NOT_A_NUMBER, number + " is not a valid number");
        }
        number = number.replaceAll("\\s+", "");
        if (!number.contains("+")) {
            number = "+" + number;
        }
        PhoneNumber phoneNmber = PhoneNumberUtil.getInstance().parse(number, null);
        String nationalNumber = String.valueOf(phoneNmber.getNationalNumber());
        String countryCode = String.valueOf(phoneNmber.getCountryCode());
        String leadingZero = phoneNmber.hasItalianLeadingZero() ? "0" : "";
        return countryCode + leadingZero + nationalNumber;
       
    }

    public static Optional<String> extractPhoneNumber(String message){
        if(message == null){
            return Optional.empty();
        }

        String number = REMOVE_NON_NUMERIC_CHARS.matcher(message).replaceAll("");
   
        try {
            return Optional.ofNullable(toInternationalFormat(number));
        } catch (NumberParseException e) {

        }

        return Optional.empty();
    }

}
