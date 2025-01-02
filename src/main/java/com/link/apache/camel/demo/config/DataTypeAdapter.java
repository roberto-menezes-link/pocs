package com.link.apache.camel.demo.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DataTypeAdapter {

    private DataTypeAdapter() {
    }

    public static LocalDate parseDate(String s) {
        if (s == null) {
            return null;
        }
        return LocalDate.parse(s);
    }

    public static String printDate(LocalDate dt) {
        if (dt == null) {
            return null;
        }

        return dt.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static LocalTime parseTime(String s) {
        if (s == null) {
            return null;
        }

        return LocalTime.parse(s);
    }

    public static String printTime(LocalTime dt) {
        if (dt == null) {
            return null;
        }

        return dt.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static LocalDateTime parseDateTime(String s) {
        if (s == null) {
            return null;
        }

        return LocalDateTime.parse(s);
    }

    public static String printDateTime(LocalDateTime dt) {
        if (dt == null) {
            return null;
        }

        return dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}