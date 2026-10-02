package com.fasterxml.jackson.databind.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ISO8601Utils {
    private static final String GMT_ID = "GMT";
    private static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone(GMT_ID);

    public static TimeZone timeZoneGMT() {
        return TIMEZONE_GMT;
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_GMT);
    }

    public static String format(Date date, boolean millis) {
        return format(date, millis, TIMEZONE_GMT);
    }

    public static String format(Date date, boolean millis, TimeZone tz) {
        Calendar calendar = new GregorianCalendar(tz, Locale.US);
        calendar.setTime(date);
        int capacity = "yyyy-MM-ddThh:mm:ss".length();
        StringBuilder formatted = new StringBuilder(capacity + (millis ? ".sss".length() : 0) + (tz.getRawOffset() == 0 ? "Z".length() : "+hh:mm".length()));
        padInt(formatted, calendar.get(1), "yyyy".length());
        formatted.append('-');
        padInt(formatted, calendar.get(2) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(5), "dd".length());
        formatted.append('T');
        padInt(formatted, calendar.get(11), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(12), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(13), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(14), "sss".length());
        }
        int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            int hours = Math.abs((offset / 60000) / 60);
            int minutes = Math.abs((offset / 60000) % 60);
            formatted.append(offset < 0 ? '-' : '+');
            padInt(formatted, hours, "hh".length());
            formatted.append(':');
            padInt(formatted, minutes, "mm".length());
        } else {
            formatted.append('Z');
        }
        return formatted.toString();
    }

    public static Date parse(String date) {
        int offset;
        String timezoneId;
        int offset2 = 0 + 4;
        try {
            int year = parseInt(date, 0, offset2);
            checkOffset(date, offset2, '-');
            int offset3 = offset2 + 1;
            int offset4 = offset3 + 2;
            int month = parseInt(date, offset3, offset4);
            checkOffset(date, offset4, '-');
            int offset5 = offset4 + 1;
            int offset6 = offset5 + 2;
            int day = parseInt(date, offset5, offset6);
            checkOffset(date, offset6, 'T');
            int offset7 = offset6 + 1;
            int offset8 = offset7 + 2;
            int hour = parseInt(date, offset7, offset8);
            checkOffset(date, offset8, ':');
            int offset9 = offset8 + 1;
            int offset10 = offset9 + 2;
            int minutes = parseInt(date, offset9, offset10);
            checkOffset(date, offset10, ':');
            int offset11 = offset10 + 1;
            int offset12 = offset11 + 2;
            int seconds = parseInt(date, offset11, offset12);
            int milliseconds = 0;
            if (date.charAt(offset12) == '.') {
                checkOffset(date, offset12, '.');
                int offset13 = offset12 + 1;
                offset2 = offset13 + 3;
                milliseconds = parseInt(date, offset13, offset2);
                offset = offset2;
            } else {
                offset = offset12;
            }
            try {
                char timezoneIndicator = date.charAt(offset);
                if (timezoneIndicator == '+' || timezoneIndicator == '-') {
                    timezoneId = GMT_ID + date.substring(offset);
                } else if (timezoneIndicator == 'Z') {
                    timezoneId = GMT_ID;
                } else {
                    throw new IndexOutOfBoundsException("Invalid time zone indicator " + timezoneIndicator);
                }
                TimeZone timezone = TimeZone.getTimeZone(timezoneId);
                if (!timezone.getID().equals(timezoneId)) {
                    throw new IndexOutOfBoundsException();
                }
                Calendar calendar = new GregorianCalendar(timezone);
                calendar.setLenient(false);
                calendar.set(1, year);
                calendar.set(2, month - 1);
                calendar.set(5, day);
                calendar.set(11, hour);
                calendar.set(12, minutes);
                calendar.set(13, seconds);
                calendar.set(14, milliseconds);
                return calendar.getTime();
            } catch (IllegalArgumentException e) {
                e = e;
                throw new IllegalArgumentException("Failed to parse date " + date, e);
            } catch (IndexOutOfBoundsException e2) {
                e = e2;
                throw new IllegalArgumentException("Failed to parse date " + date, e);
            } catch (NumberFormatException e3) {
                e = e3;
                throw new IllegalArgumentException("Failed to parse date " + date, e);
            }
        } catch (IllegalArgumentException e4) {
            e = e4;
        } catch (IndexOutOfBoundsException e5) {
            e = e5;
        } catch (NumberFormatException e6) {
            e = e6;
        }
    }

    private static void checkOffset(String value, int offset, char expected) throws IndexOutOfBoundsException {
        char found = value.charAt(offset);
        if (found != expected) {
            throw new IndexOutOfBoundsException("Expected '" + expected + "' character but found '" + found + "'");
        }
    }

    private static int parseInt(String value, int beginIndex, int endIndex) throws NumberFormatException {
        int i;
        if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
            throw new NumberFormatException(value);
        }
        int result = 0;
        if (beginIndex >= endIndex) {
            i = beginIndex;
        } else {
            i = beginIndex + 1;
            int digit = Character.digit(value.charAt(beginIndex), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + value);
            }
            result = -digit;
        }
        while (i < endIndex) {
            int i2 = i + 1;
            int digit2 = Character.digit(value.charAt(i), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + value);
            }
            result = (result * 10) - digit2;
            i = i2;
        }
        return -result;
    }

    private static void padInt(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }
}
