package com.ct.fitorto.utils;

import java.util.regex.Pattern;

/**
 * Created by Ankit on 1/25/2016.
 */
public class PatternUtil {
    /**
     * To check pattern for indian mobile number i.e starting from 789
     */
    public static final Pattern INDIAN_MOBILE_NUMBER
            = Pattern.compile("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}");
}
