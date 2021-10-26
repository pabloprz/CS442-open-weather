package com.iit.pab.openweather.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormattingUtils {

    public static String formatTime(LocalDateTime ldt) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());
        return ldt.format(dtf);
    }

    public static String formatDateTime(LocalDateTime ldt) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd h:mm a, yyyy",
                Locale.getDefault());
        return ldt.format(dtf);
    }

    public static String formatDate(LocalDateTime ldt) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, dd/MM", Locale.getDefault());
        return ldt.format(dtf);
    }

    public static String tempToText(double temp, TempUnit unit) {
        return String.format(Locale.getDefault(), "%.0fº" + unit.getSymbol(), temp);
    }

    public static String capitalize(String str) {
        return String.format("%s%s", Character.toUpperCase(str.charAt(0)), str.substring(1));
    }

}
