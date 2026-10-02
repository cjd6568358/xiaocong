package com.google.gson.internal.bind.util;

import com.tencent.android.tpush.common.Constants;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

    /* JADX WARN: Code duplicated, block: B:48:0x0150  */
    /* JADX WARN: Code duplicated, block: B:51:0x0157  */
    /* JADX WARN: Code duplicated, block: B:53:0x015d  */
    /* JADX WARN: Code duplicated, block: B:87:0x0315  */
    public static Date parse(String date, ParsePosition pos) throws ParseException {
        Exception fail;
        String input;
        String msg;
        int offset;
        TimeZone timezone;
        char c;
        try {
            int offset2 = pos.getIndex();
            int offset3 = offset2 + 4;
            int year = parseInt(date, offset2, offset3);
            if (checkOffset(date, offset3, '-')) {
                offset3++;
            }
            int offset4 = offset3 + 2;
            int month = parseInt(date, offset3, offset4);
            int offset5 = checkOffset(date, offset4, '-') ? offset4 + 1 : offset4;
            int offset6 = offset5 + 2;
            int day = parseInt(date, offset5, offset6);
            int hour = 0;
            int minutes = 0;
            int seconds = 0;
            int milliseconds = 0;
            boolean hasT = checkOffset(date, offset6, 'T');
            if (!hasT && date.length() <= offset6) {
                Calendar calendar = new GregorianCalendar(year, month - 1, day);
                pos.setIndex(offset6);
                return calendar.getTime();
            }
            if (hasT) {
                int offset7 = offset6 + 1;
                int offset8 = offset7 + 2;
                hour = parseInt(date, offset7, offset8);
                if (checkOffset(date, offset8, ':')) {
                    offset8++;
                }
                int offset9 = offset8 + 2;
                minutes = parseInt(date, offset8, offset9);
                int offset10 = checkOffset(date, offset9, ':') ? offset9 + 1 : offset9;
                if (date.length() <= offset10 || (c = date.charAt(offset10)) == 'Z' || c == '+' || c == '-') {
                    offset6 = offset10;
                } else {
                    offset6 = offset10 + 2;
                    seconds = parseInt(date, offset10, offset6);
                    if (seconds > 59 && seconds < 63) {
                        seconds = 59;
                    }
                    if (checkOffset(date, offset6, '.')) {
                        int offset11 = offset6 + 1;
                        int endOffset = indexOfNonDigit(date, offset11 + 1);
                        int parseEndOffset = Math.min(endOffset, offset11 + 3);
                        int fraction = parseInt(date, offset11, parseEndOffset);
                        switch (parseEndOffset - offset11) {
                            case 1:
                                milliseconds = fraction * 100;
                                break;
                            case 2:
                                milliseconds = fraction * 10;
                                break;
                            default:
                                milliseconds = fraction;
                                break;
                        }
                        offset6 = endOffset;
                    }
                }
            }
            if (date.length() <= offset6) {
                throw new IllegalArgumentException("No time zone indicator");
            }
            char timezoneIndicator = date.charAt(offset6);
            if (timezoneIndicator == 'Z') {
                timezone = TIMEZONE_UTC;
                offset = offset6 + 1;
            } else if (timezoneIndicator == '+' || timezoneIndicator == '-') {
                String timezoneOffset = date.substring(offset6);
                if (timezoneOffset.length() < 5) {
                    timezoneOffset = timezoneOffset + "00";
                }
                offset = offset6 + timezoneOffset.length();
                if ("+0000".equals(timezoneOffset) || "+00:00".equals(timezoneOffset)) {
                    timezone = TIMEZONE_UTC;
                } else {
                    String timezoneId = "GMT" + timezoneOffset;
                    timezone = TimeZone.getTimeZone(timezoneId);
                    String act = timezone.getID();
                    if (!act.equals(timezoneId)) {
                        String cleaned = act.replace(":", Constants.MAIN_VERSION_TAG);
                        if (!cleaned.equals(timezoneId)) {
                            throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + timezoneId + " given, resolves to " + timezone.getID());
                        }
                    }
                }
            } else {
                throw new IndexOutOfBoundsException("Invalid time zone indicator '" + timezoneIndicator + "'");
            }
            Calendar calendar2 = new GregorianCalendar(timezone);
            calendar2.setLenient(false);
            calendar2.set(1, year);
            calendar2.set(2, month - 1);
            calendar2.set(5, day);
            calendar2.set(11, hour);
            calendar2.set(12, minutes);
            calendar2.set(13, seconds);
            calendar2.set(14, milliseconds);
            pos.setIndex(offset);
            return calendar2.getTime();
        } catch (IllegalArgumentException e) {
            fail = e;
            if (date == null) {
                input = null;
            } else {
                input = '\"' + date + "'";
            }
            msg = fail.getMessage();
            if (msg != null || msg.isEmpty()) {
                msg = "(" + fail.getClass().getName() + ")";
            }
            ParseException ex = new ParseException("Failed to parse date [" + input + "]: " + msg, pos.getIndex());
            ex.initCause(fail);
            throw ex;
        } catch (IndexOutOfBoundsException e2) {
            fail = e2;
            if (date == null) {
                input = null;
            } else {
                input = '\"' + date + "'";
            }
            msg = fail.getMessage();
            if (msg != null) {
                msg = "(" + fail.getClass().getName() + ")";
            } else {
                msg = "(" + fail.getClass().getName() + ")";
            }
            ParseException ex2 = new ParseException("Failed to parse date [" + input + "]: " + msg, pos.getIndex());
            ex2.initCause(fail);
            throw ex2;
        } catch (NumberFormatException e3) {
            fail = e3;
            if (date == null) {
                input = null;
            } else {
                input = '\"' + date + "'";
            }
            msg = fail.getMessage();
            if (msg != null) {
                msg = "(" + fail.getClass().getName() + ")";
            } else {
                msg = "(" + fail.getClass().getName() + ")";
            }
            ParseException ex3 = new ParseException("Failed to parse date [" + input + "]: " + msg, pos.getIndex());
            ex3.initCause(fail);
            throw ex3;
        }
    }

    private static boolean checkOffset(String value, int offset, char expected) {
        return offset < value.length() && value.charAt(offset) == expected;
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
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
            result = -digit;
        }
        while (i < endIndex) {
            int i2 = i + 1;
            int digit2 = Character.digit(value.charAt(i), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
            result = (result * 10) - digit2;
            i = i2;
        }
        return -result;
    }

    private static int indexOfNonDigit(String string, int offset) {
        int i = offset;
        while (i < string.length()) {
            char c = string.charAt(i);
            if (c >= '0' && c <= '9') {
                i++;
            } else {
                return i;
            }
        }
        int i2 = string.length();
        return i2;
    }
}
