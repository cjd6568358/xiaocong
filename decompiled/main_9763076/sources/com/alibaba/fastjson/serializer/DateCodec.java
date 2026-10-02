package com.alibaba.fastjson.serializer;

import bsh.ParserConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DateCodec extends AbstractDateDeserializer implements ObjectDeserializer, ObjectSerializer {
    public static final DateCodec instance = new DateCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        Date date;
        char[] buf;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        if (object instanceof Date) {
            date = (Date) object;
        } else {
            date = TypeUtils.castToDate(object);
        }
        if (out.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            DateFormat format = serializer.getDateFormat();
            if (format == null) {
                format = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT, serializer.locale);
                format.setTimeZone(serializer.timeZone);
            }
            String text = format.format(date);
            out.writeString(text);
            return;
        }
        if (out.isEnabled(SerializerFeature.WriteClassName) && object.getClass() != fieldType) {
            if (object.getClass() == Date.class) {
                out.write("new Date(");
                out.writeLong(((Date) object).getTime());
                out.write(41);
                return;
            } else {
                out.write(ParserConstants.ANDASSIGNX);
                out.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                serializer.write(object.getClass().getName());
                out.writeFieldValue(',', "val", ((Date) object).getTime());
                out.write(ParserConstants.ORASSIGNX);
                return;
            }
        }
        long time = date.getTime();
        if (out.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
            char quote = out.isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : '\"';
            out.write(quote);
            Calendar calendar = Calendar.getInstance(serializer.timeZone, serializer.locale);
            calendar.setTimeInMillis(time);
            int year = calendar.get(1);
            int month = calendar.get(2) + 1;
            int day = calendar.get(5);
            int hour = calendar.get(11);
            int minute = calendar.get(12);
            int second = calendar.get(13);
            int millis = calendar.get(14);
            if (millis != 0) {
                buf = "0000-00-00T00:00:00.000".toCharArray();
                IOUtils.getChars(millis, 23, buf);
                IOUtils.getChars(second, 19, buf);
                IOUtils.getChars(minute, 16, buf);
                IOUtils.getChars(hour, 13, buf);
                IOUtils.getChars(day, 10, buf);
                IOUtils.getChars(month, 7, buf);
                IOUtils.getChars(year, 4, buf);
            } else if (second == 0 && minute == 0 && hour == 0) {
                buf = "0000-00-00".toCharArray();
                IOUtils.getChars(day, 10, buf);
                IOUtils.getChars(month, 7, buf);
                IOUtils.getChars(year, 4, buf);
            } else {
                buf = "0000-00-00T00:00:00".toCharArray();
                IOUtils.getChars(second, 19, buf);
                IOUtils.getChars(minute, 16, buf);
                IOUtils.getChars(hour, 13, buf);
                IOUtils.getChars(day, 10, buf);
                IOUtils.getChars(month, 7, buf);
                IOUtils.getChars(year, 4, buf);
            }
            out.write(buf);
            int timeZone = calendar.getTimeZone().getRawOffset() / 3600000;
            if (timeZone == 0) {
                out.write(90);
            } else {
                if (timeZone > 0) {
                    out.append('+').append((CharSequence) String.format("%02d", Integer.valueOf(timeZone)));
                } else {
                    out.append('-').append((CharSequence) String.format("%02d", Integer.valueOf(-timeZone)));
                }
                out.append((CharSequence) ":00");
            }
            out.write(quote);
            return;
        }
        out.writeLong(time);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [T, java.util.Calendar] */
    /* JADX WARN: Type inference failed for: r2v5, types: [T, java.util.Calendar] */
    @Override // com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer
    public <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        if (obj2 == 0) {
            return null;
        }
        if (obj2 instanceof Date) {
            return obj2;
        }
        if (obj2 instanceof Number) {
            return (T) new Date(((Number) obj2).longValue());
        }
        if (obj2 instanceof String) {
            String strSubstring = (String) obj2;
            if (strSubstring.length() == 0) {
                return null;
            }
            JSONScanner jSONScanner = new JSONScanner(strSubstring);
            try {
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    ?? r2 = (T) jSONScanner.getCalendar();
                    if (type != Calendar.class) {
                        T t = (T) r2.getTime();
                        jSONScanner.close();
                        return t;
                    }
                    jSONScanner.close();
                    return r2;
                }
                jSONScanner.close();
                if (strSubstring.length() == defaultJSONParser.getDateFomartPattern().length() || (strSubstring.length() == 22 && defaultJSONParser.getDateFomartPattern().equals("yyyyMMddHHmmssSSSZ"))) {
                    try {
                        return (T) defaultJSONParser.getDateFormat().parse(strSubstring);
                    } catch (ParseException e) {
                    }
                }
                if (strSubstring.startsWith("/Date(") && strSubstring.endsWith(")/")) {
                    strSubstring = strSubstring.substring(6, strSubstring.length() - 2);
                }
                if ("0000-00-00".equals(strSubstring) || "0000-00-00T00:00:00".equalsIgnoreCase(strSubstring) || "0001-01-01T00:00:00+08:00".equalsIgnoreCase(strSubstring)) {
                    return null;
                }
                int iLastIndexOf = strSubstring.lastIndexOf(ParserConstants.ORASSIGN);
                if (iLastIndexOf > 20) {
                    TimeZone timeZone = TimeZone.getTimeZone(strSubstring.substring(iLastIndexOf + 1));
                    if (!"GMT".equals(timeZone.getID())) {
                        JSONScanner jSONScanner2 = new JSONScanner(strSubstring.substring(0, iLastIndexOf));
                        try {
                            if (jSONScanner2.scanISO8601DateIfMatch(false)) {
                                ?? r3 = (T) jSONScanner2.getCalendar();
                                r3.setTimeZone(timeZone);
                                if (type != Calendar.class) {
                                    return (T) r3.getTime();
                                }
                                return r3;
                            }
                        } finally {
                            jSONScanner2.close();
                        }
                    }
                }
                return (T) new Date(Long.parseLong(strSubstring));
            } catch (Throwable th) {
                jSONScanner.close();
                throw th;
            }
        }
        throw new JSONException("parse error");
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
