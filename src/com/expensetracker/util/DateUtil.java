package com.expensetracker.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;
    public static LocalDate parse(String s) { return LocalDate.parse(s, F); }
    public static String fmt(LocalDate d) { return d.format(F); }
}
