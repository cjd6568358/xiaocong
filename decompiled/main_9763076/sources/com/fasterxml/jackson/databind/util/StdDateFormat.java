package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.io.NumberInput;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StdDateFormat extends DateFormat {
    protected static final DateFormat DATE_FORMAT_ISO8601;
    protected static final DateFormat DATE_FORMAT_ISO8601_Z;
    protected static final DateFormat DATE_FORMAT_PLAIN;
    protected static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final StdDateFormat instance;
    protected transient DateFormat _formatISO8601;
    protected transient DateFormat _formatISO8601_z;
    protected transient DateFormat _formatPlain;
    protected transient DateFormat _formatRFC1123;
    protected transient TimeZone _timezone;
    protected static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    protected static final String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    protected static final String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
    protected static final String[] ALL_FORMATS = {DATE_FORMAT_STR_ISO8601, DATE_FORMAT_STR_ISO8601_Z, "EEE, dd MMM yyyy HH:mm:ss zzz", DATE_FORMAT_STR_PLAIN};
    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("GMT");
    protected static final DateFormat DATE_FORMAT_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

    static {
        DATE_FORMAT_RFC1123.setTimeZone(DEFAULT_TIMEZONE);
        DATE_FORMAT_ISO8601 = new SimpleDateFormat(DATE_FORMAT_STR_ISO8601);
        DATE_FORMAT_ISO8601.setTimeZone(DEFAULT_TIMEZONE);
        DATE_FORMAT_ISO8601_Z = new SimpleDateFormat(DATE_FORMAT_STR_ISO8601_Z);
        DATE_FORMAT_ISO8601_Z.setTimeZone(DEFAULT_TIMEZONE);
        DATE_FORMAT_PLAIN = new SimpleDateFormat(DATE_FORMAT_STR_PLAIN);
        DATE_FORMAT_PLAIN.setTimeZone(DEFAULT_TIMEZONE);
        instance = new StdDateFormat();
    }

    public StdDateFormat() {
    }

    public StdDateFormat(TimeZone tz) {
        this._timezone = tz;
    }

    public static TimeZone getDefaultTimeZone() {
        return DEFAULT_TIMEZONE;
    }

    public StdDateFormat withTimeZone(TimeZone tz) {
        if (tz == null) {
            tz = DEFAULT_TIMEZONE;
        }
        return new StdDateFormat(tz);
    }

    @Override // java.text.DateFormat, java.text.Format
    public StdDateFormat clone() {
        return new StdDateFormat();
    }

    public static DateFormat getBlueprintISO8601Format() {
        return DATE_FORMAT_ISO8601;
    }

    public static DateFormat getISO8601Format(TimeZone tz) {
        return _cloneFormat(DATE_FORMAT_ISO8601, tz);
    }

    public static DateFormat getBlueprintRFC1123Format() {
        return DATE_FORMAT_RFC1123;
    }

    public static DateFormat getRFC1123Format(TimeZone tz) {
        return _cloneFormat(DATE_FORMAT_RFC1123, tz);
    }

    @Override // java.text.DateFormat
    public void setTimeZone(TimeZone tz) {
        if (tz != this._timezone) {
            this._formatRFC1123 = null;
            this._formatISO8601 = null;
            this._formatISO8601_z = null;
            this._formatPlain = null;
            this._timezone = tz;
        }
    }

    @Override // java.text.DateFormat
    public Date parse(String dateStr) throws ParseException {
        String dateStr2 = dateStr.trim();
        ParsePosition pos = new ParsePosition(0);
        Date result = parse(dateStr2, pos);
        if (result != null) {
            return result;
        }
        StringBuilder sb = new StringBuilder();
        String[] arr$ = ALL_FORMATS;
        for (String f : arr$) {
            if (sb.length() > 0) {
                sb.append("\", \"");
            } else {
                sb.append('\"');
            }
            sb.append(f);
        }
        sb.append('\"');
        throw new ParseException(String.format("Can not parse date \"%s\": not compatible with any of standard forms (%s)", dateStr2, sb.toString()), pos.getErrorIndex());
    }

    @Override // java.text.DateFormat
    public Date parse(String dateStr, ParsePosition pos) {
        char ch;
        if (looksLikeISO8601(dateStr)) {
            return parseAsISO8601(dateStr, pos);
        }
        int i = dateStr.length();
        do {
            i--;
            if (i < 0 || (ch = dateStr.charAt(i)) < '0') {
                break;
            }
        } while (ch <= '9');
        if (i < 0 && NumberInput.inLongRange(dateStr, false)) {
            return new Date(Long.parseLong(dateStr));
        }
        return parseAsRFC1123(dateStr, pos);
    }

    @Override // java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        if (this._formatISO8601 == null) {
            this._formatISO8601 = _cloneFormat(DATE_FORMAT_ISO8601);
        }
        return this._formatISO8601.format(date, toAppendTo, fieldPosition);
    }

    protected boolean looksLikeISO8601(String dateStr) {
        return dateStr.length() >= 5 && Character.isDigit(dateStr.charAt(0)) && Character.isDigit(dateStr.charAt(3)) && dateStr.charAt(4) == '-';
    }

    protected Date parseAsISO8601(String dateStr, ParsePosition pos) {
        DateFormat df;
        int len = dateStr.length();
        char c = dateStr.charAt(len - 1);
        if (len <= 10 && Character.isDigit(c)) {
            df = this._formatPlain;
            if (df == null) {
                df = _cloneFormat(DATE_FORMAT_PLAIN);
                this._formatPlain = df;
            }
        } else if (c == 'Z') {
            df = this._formatISO8601_z;
            if (df == null) {
                df = _cloneFormat(DATE_FORMAT_ISO8601_Z);
                this._formatISO8601_z = df;
            }
            if (dateStr.charAt(len - 4) == ':') {
                StringBuilder sb = new StringBuilder(dateStr);
                sb.insert(len - 1, ".000");
                dateStr = sb.toString();
            }
        } else if (hasTimeZone(dateStr)) {
            char c2 = dateStr.charAt(len - 3);
            if (c2 == ':') {
                StringBuilder sb2 = new StringBuilder(dateStr);
                sb2.delete(len - 3, len - 2);
                dateStr = sb2.toString();
            } else if (c2 == '+' || c2 == '-') {
                dateStr = dateStr + "00";
            }
            int len2 = dateStr.length();
            if (Character.isDigit(dateStr.charAt(len2 - 9))) {
                StringBuilder sb3 = new StringBuilder(dateStr);
                sb3.insert(len2 - 5, ".000");
                dateStr = sb3.toString();
            }
            df = this._formatISO8601;
            if (this._formatISO8601 == null) {
                df = _cloneFormat(DATE_FORMAT_ISO8601);
                this._formatISO8601 = df;
            }
        } else {
            StringBuilder sb4 = new StringBuilder(dateStr);
            int timeLen = (len - dateStr.lastIndexOf(84)) - 1;
            if (timeLen <= 8) {
                sb4.append(".000");
            }
            sb4.append('Z');
            dateStr = sb4.toString();
            df = this._formatISO8601_z;
            if (df == null) {
                df = _cloneFormat(DATE_FORMAT_ISO8601_Z);
                this._formatISO8601_z = df;
            }
        }
        return df.parse(dateStr, pos);
    }

    protected Date parseAsRFC1123(String dateStr, ParsePosition pos) {
        if (this._formatRFC1123 == null) {
            this._formatRFC1123 = _cloneFormat(DATE_FORMAT_RFC1123);
        }
        return this._formatRFC1123.parse(dateStr, pos);
    }

    private static final boolean hasTimeZone(String str) {
        char c;
        char c2;
        char c3;
        int len = str.length();
        return len >= 6 && ((c = str.charAt(len + (-6))) == '+' || c == '-' || (c2 = str.charAt(len + (-5))) == '+' || c2 == '-' || (c3 = str.charAt(len + (-3))) == '+' || c3 == '-');
    }

    private final DateFormat _cloneFormat(DateFormat df) {
        return _cloneFormat(df, this._timezone);
    }

    private static final DateFormat _cloneFormat(DateFormat df, TimeZone tz) {
        DateFormat df2 = (DateFormat) df.clone();
        if (tz != null) {
            df2.setTimeZone(tz);
        }
        return df2;
    }
}
