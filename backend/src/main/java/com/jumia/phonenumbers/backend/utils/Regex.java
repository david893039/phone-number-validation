package com.jumia.phonenumbers.backend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static boolean validate(String regExr, String s) {
        Pattern REGEX;
        REGEX = Pattern.compile(regExr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = REGEX.matcher(s);
        return matcher.find();
    }
}
