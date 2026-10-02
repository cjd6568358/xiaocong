package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextObjectSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Jdk8DateCodec extends ContextObjectDeserializer implements ObjectDeserializer, ContextObjectSerializer, ObjectSerializer {
    public static final Jdk8DateCodec instance = new Jdk8DateCodec();
    private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormatter.ofPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormatter.ofPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormatter.ofPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormatter.ofPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        LocalDateTime localDateTimeOf;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (jSONLexer.token() == 4) {
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextToken();
            DateTimeFormatter dateTimeFormatterOfPattern = null;
            if (str != null) {
                if ("yyyy-MM-dd HH:mm:ss".equals(str)) {
                    dateTimeFormatterOfPattern = defaultFormatter;
                } else {
                    dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern(str);
                }
            }
            if (Constants.MAIN_VERSION_TAG.equals(strStringVal)) {
                return null;
            }
            if (type == LocalDateTime.class) {
                if (strStringVal.length() == 10 || strStringVal.length() == 8) {
                    localDateTimeOf = LocalDateTime.of(parseLocalDate(strStringVal, str, dateTimeFormatterOfPattern), LocalTime.MIN);
                } else {
                    localDateTimeOf = parseDateTime(strStringVal, dateTimeFormatterOfPattern);
                }
                return (T) localDateTimeOf;
            }
            if (type == LocalDate.class) {
                if (strStringVal.length() == 23) {
                    LocalDateTime localDateTime = LocalDateTime.parse(strStringVal);
                    return (T) LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
                }
                return (T) parseLocalDate(strStringVal, str, dateTimeFormatterOfPattern);
            }
            if (type == LocalTime.class) {
                if (strStringVal.length() == 23) {
                    LocalDateTime localDateTime2 = LocalDateTime.parse(strStringVal);
                    return (T) LocalTime.of(localDateTime2.getHour(), localDateTime2.getMinute(), localDateTime2.getSecond(), localDateTime2.getNano());
                }
                return (T) LocalTime.parse(strStringVal);
            }
            if (type == ZonedDateTime.class) {
                if (dateTimeFormatterOfPattern == defaultFormatter) {
                    dateTimeFormatterOfPattern = ISO_FIXED_FORMAT;
                }
                return (T) parseZonedDateTime(strStringVal, dateTimeFormatterOfPattern);
            }
            if (type == OffsetDateTime.class) {
                return (T) OffsetDateTime.parse(strStringVal);
            }
            if (type == OffsetTime.class) {
                return (T) OffsetTime.parse(strStringVal);
            }
            if (type == ZoneId.class) {
                return (T) ZoneId.of(strStringVal);
            }
            if (type == Period.class) {
                return (T) Period.parse(strStringVal);
            }
            if (type == Duration.class) {
                return (T) Duration.parse(strStringVal);
            }
            if (type == Instant.class) {
                return (T) Instant.parse(strStringVal);
            }
            return null;
        }
        throw new UnsupportedOperationException();
    }

    protected LocalDateTime parseDateTime(String text, DateTimeFormatter formatter) {
        if (formatter == null) {
            if (text.length() == 19) {
                char c4 = text.charAt(4);
                char c7 = text.charAt(7);
                char c10 = text.charAt(10);
                char c13 = text.charAt(13);
                char c16 = text.charAt(16);
                if (c13 == ':' && c16 == ':') {
                    if (c4 == '-' && c7 == '-') {
                        if (c10 == 'T') {
                            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        } else if (c10 == ' ') {
                            formatter = defaultFormatter;
                        }
                    } else if (c4 == '-' && c7 == '-') {
                        formatter = defaultFormatter;
                    } else if (c4 == '/' && c7 == '/') {
                        formatter = formatter_dt19_tw;
                    } else {
                        char c0 = text.charAt(0);
                        char c1 = text.charAt(1);
                        char c2 = text.charAt(2);
                        char c3 = text.charAt(3);
                        char c5 = text.charAt(5);
                        if (c2 == '/' && c5 == '/') {
                            int v0 = ((c0 - '0') * 10) + (c1 - '0');
                            int v1 = ((c3 - '0') * 10) + (c4 - '0');
                            if (v0 > 12) {
                                formatter = formatter_dt19_eur;
                            } else if (v1 > 12) {
                                formatter = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    formatter = formatter_dt19_us;
                                } else if (country.equals("BR") || country.equals("AU")) {
                                    formatter = formatter_dt19_eur;
                                }
                            }
                        } else if (c2 == '.' && c5 == '.') {
                            formatter = formatter_dt19_de;
                        } else if (c2 == '-' && c5 == '-') {
                            formatter = formatter_dt19_in;
                        }
                    }
                }
            }
            if (text.length() >= 17) {
                char c6 = text.charAt(4);
                if (c6 == 24180) {
                    if (text.charAt(text.length() - 1) == 31186) {
                        formatter = formatter_dt19_cn_1;
                    } else {
                        formatter = formatter_dt19_cn;
                    }
                } else if (c6 == 45380) {
                    formatter = formatter_dt19_kr;
                }
            }
        }
        if (formatter == null) {
            return LocalDateTime.parse(text);
        }
        return LocalDateTime.parse(text, formatter);
    }

    protected LocalDate parseLocalDate(String text, String format, DateTimeFormatter formatter) {
        if (formatter == null) {
            if (text.length() == 8) {
                formatter = formatter_d8;
            }
            if (text.length() == 10) {
                char c4 = text.charAt(4);
                char c7 = text.charAt(7);
                if (c4 == '/' && c7 == '/') {
                    formatter = formatter_d10_tw;
                }
                char c0 = text.charAt(0);
                char c1 = text.charAt(1);
                char c2 = text.charAt(2);
                char c3 = text.charAt(3);
                char c5 = text.charAt(5);
                if (c2 == '/' && c5 == '/') {
                    int v0 = ((c0 - '0') * 10) + (c1 - '0');
                    int v1 = ((c3 - '0') * 10) + (c4 - '0');
                    if (v0 > 12) {
                        formatter = formatter_d10_eur;
                    } else if (v1 > 12) {
                        formatter = formatter_d10_us;
                    } else {
                        String country = Locale.getDefault().getCountry();
                        if (country.equals("US")) {
                            formatter = formatter_d10_us;
                        } else if (country.equals("BR") || country.equals("AU")) {
                            formatter = formatter_d10_eur;
                        }
                    }
                } else if (c2 == '.' && c5 == '.') {
                    formatter = formatter_d10_de;
                } else if (c2 == '-' && c5 == '-') {
                    formatter = formatter_d10_in;
                }
            }
            if (text.length() >= 9) {
                char c6 = text.charAt(4);
                if (c6 == 24180) {
                    formatter = formatter_d10_cn;
                } else if (c6 == 45380) {
                    formatter = formatter_d10_kr;
                }
            }
        }
        if (formatter == null) {
            return LocalDate.parse(text);
        }
        return LocalDate.parse(text, formatter);
    }

    protected ZonedDateTime parseZonedDateTime(String text, DateTimeFormatter formatter) {
        if (formatter == null) {
            if (text.length() == 19) {
                char c4 = text.charAt(4);
                char c7 = text.charAt(7);
                char c10 = text.charAt(10);
                char c13 = text.charAt(13);
                char c16 = text.charAt(16);
                if (c13 == ':' && c16 == ':') {
                    if (c4 == '-' && c7 == '-') {
                        if (c10 == 'T') {
                            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        } else if (c10 == ' ') {
                            formatter = defaultFormatter;
                        }
                    } else if (c4 == '-' && c7 == '-') {
                        formatter = defaultFormatter;
                    } else if (c4 == '/' && c7 == '/') {
                        formatter = formatter_dt19_tw;
                    } else {
                        char c0 = text.charAt(0);
                        char c1 = text.charAt(1);
                        char c2 = text.charAt(2);
                        char c3 = text.charAt(3);
                        char c5 = text.charAt(5);
                        if (c2 == '/' && c5 == '/') {
                            int v0 = ((c0 - '0') * 10) + (c1 - '0');
                            int v1 = ((c3 - '0') * 10) + (c4 - '0');
                            if (v0 > 12) {
                                formatter = formatter_dt19_eur;
                            } else if (v1 > 12) {
                                formatter = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    formatter = formatter_dt19_us;
                                } else if (country.equals("BR") || country.equals("AU")) {
                                    formatter = formatter_dt19_eur;
                                }
                            }
                        } else if (c2 == '.' && c5 == '.') {
                            formatter = formatter_dt19_de;
                        } else if (c2 == '-' && c5 == '-') {
                            formatter = formatter_dt19_in;
                        }
                    }
                }
            }
            if (text.length() >= 17) {
                char c6 = text.charAt(4);
                if (c6 == 24180) {
                    if (text.charAt(text.length() - 1) == 31186) {
                        formatter = formatter_dt19_cn_1;
                    } else {
                        formatter = formatter_dt19_cn;
                    }
                } else if (c6 == 45380) {
                    formatter = formatter_dt19_kr;
                }
            }
        }
        if (formatter == null) {
            return ZonedDateTime.parse(text);
        }
        return ZonedDateTime.parse(text, formatter);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        if (fieldType == null) {
            fieldType = object.getClass();
        }
        if (fieldType == LocalDateTime.class) {
            int mask = SerializerFeature.UseISO8601DateFormat.getMask();
            LocalDateTime dateTime = (LocalDateTime) object;
            String format = serializer.getDateFormatPattern();
            if ((format == null && (features & mask) != 0) || serializer.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
                format = "yyyy-MM-dd'T'HH:mm:ss";
            }
            if (dateTime.getNano() == 0 || format != null) {
                if (format == null) {
                    format = JSON.DEFFAULT_DATE_FORMAT;
                }
                write(out, dateTime, format);
                return;
            }
            out.writeString(object.toString());
            return;
        }
        out.writeString(object.toString());
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer serializer, Object object, BeanContext context) throws IOException {
        SerializeWriter out = serializer.out;
        String format = context.getFormat();
        write(out, (TemporalAccessor) object, format);
    }

    private void write(SerializeWriter out, TemporalAccessor object, String format) {
        DateTimeFormatter formatter;
        if (format == "yyyy-MM-dd'T'HH:mm:ss") {
            formatter = formatter_iso8601;
        } else {
            formatter = DateTimeFormatter.ofPattern(format);
        }
        String text = formatter.format(object);
        out.writeString(text);
    }
}
