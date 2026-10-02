package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DateDeserializers {
    public static StdDeserializer<?>[] all() {
        return new StdDeserializer[]{new CalendarDeserializer(), new DateDeserializer(), new CalendarDeserializer(GregorianCalendar.class), new SqlDateDeserializer(), new TimestampDeserializer(), new TimeZoneDeserializer()};
    }

    protected static abstract class DateBasedDeserializer<T> extends StdScalarDeserializer<T> implements ContextualDeserializer {
        protected final DateFormat _customFormat;
        protected final String _formatString;

        /* JADX INFO: renamed from: withDateFormat */
        protected abstract DateBasedDeserializer<T> withDateFormat2(DateFormat dateFormat, String str);

        protected DateBasedDeserializer(Class<?> clz) {
            super(clz);
            this._customFormat = null;
            this._formatString = null;
        }

        protected DateBasedDeserializer(DateBasedDeserializer<T> base, DateFormat format, String formatStr) {
            super(base._valueClass);
            this._customFormat = format;
            this._formatString = formatStr;
        }

        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            JsonFormat.Value format;
            DateFormat df;
            if (property != null && (format = ctxt.getAnnotationIntrospector().findFormat(property.getMember())) != null) {
                TimeZone tz = format.getTimeZone();
                String pattern = format.getPattern();
                if (pattern.length() > 0) {
                    Locale loc = format.getLocale();
                    if (loc == null) {
                        loc = ctxt.getLocale();
                    }
                    SimpleDateFormat df2 = new SimpleDateFormat(pattern, loc);
                    if (tz == null) {
                        tz = ctxt.getTimeZone();
                    }
                    df2.setTimeZone(tz);
                    return withDateFormat2(df2, pattern);
                }
                if (tz != null) {
                    DateFormat df3 = ctxt.getConfig().getDateFormat();
                    if (df3.getClass() == StdDateFormat.class) {
                        df = ((StdDateFormat) df3).withTimeZone(tz);
                    } else {
                        df = (DateFormat) df3.clone();
                        df.setTimeZone(tz);
                    }
                    return withDateFormat2(df, pattern);
                }
                return this;
            }
            return this;
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer
        protected Date _parseDate(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Date date;
            if (this._customFormat != null && jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
                String strTrim = jsonParser.getText().trim();
                if (strTrim.length() == 0) {
                    return (Date) getEmptyValue();
                }
                synchronized (this._customFormat) {
                    try {
                        date = this._customFormat.parse(strTrim);
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Failed to parse Date value '" + strTrim + "' (format: \"" + this._formatString + "\"): " + e.getMessage());
                    }
                }
                return date;
            }
            return super._parseDate(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static class CalendarDeserializer extends DateBasedDeserializer<Calendar> {
        protected final Class<? extends Calendar> _calendarClass;

        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer, com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public /* bridge */ /* synthetic */ JsonDeserializer createContextual(DeserializationContext x0, BeanProperty x1) throws JsonMappingException {
            return super.createContextual(x0, x1);
        }

        public CalendarDeserializer() {
            super(Calendar.class);
            this._calendarClass = null;
        }

        public CalendarDeserializer(Class<? extends Calendar> cc) {
            super(cc);
            this._calendarClass = cc;
        }

        public CalendarDeserializer(CalendarDeserializer src, DateFormat df, String formatString) {
            super(src, df, formatString);
            this._calendarClass = src._calendarClass;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer
        /* JADX INFO: renamed from: withDateFormat, reason: merged with bridge method [inline-methods] */
        public DateBasedDeserializer<Calendar> withDateFormat2(DateFormat df, String formatString) {
            return new CalendarDeserializer(this, df, formatString);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Calendar deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            Date d = _parseDate(jp, ctxt);
            if (d == null) {
                return null;
            }
            if (this._calendarClass == null) {
                return ctxt.constructCalendar(d);
            }
            try {
                Calendar c = this._calendarClass.newInstance();
                c.setTimeInMillis(d.getTime());
                return c;
            } catch (Exception e) {
                throw ctxt.instantiationException(this._calendarClass, e);
            }
        }
    }

    public static class DateDeserializer extends DateBasedDeserializer<Date> {
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer, com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public /* bridge */ /* synthetic */ JsonDeserializer createContextual(DeserializationContext x0, BeanProperty x1) throws JsonMappingException {
            return super.createContextual(x0, x1);
        }

        public DateDeserializer() {
            super(Date.class);
        }

        public DateDeserializer(DateDeserializer base, DateFormat df, String formatString) {
            super(base, df, formatString);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer
        /* JADX INFO: renamed from: withDateFormat, reason: avoid collision after fix types in other method */
        public DateBasedDeserializer<Date> withDateFormat2(DateFormat df, String formatString) {
            return new DateDeserializer(this, df, formatString);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return _parseDate(jp, ctxt);
        }
    }

    public static class SqlDateDeserializer extends DateBasedDeserializer<java.sql.Date> {
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer, com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public /* bridge */ /* synthetic */ JsonDeserializer createContextual(DeserializationContext x0, BeanProperty x1) throws JsonMappingException {
            return super.createContextual(x0, x1);
        }

        public SqlDateDeserializer() {
            super(java.sql.Date.class);
        }

        public SqlDateDeserializer(SqlDateDeserializer src, DateFormat df, String formatString) {
            super(src, df, formatString);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer
        /* JADX INFO: renamed from: withDateFormat, reason: avoid collision after fix types in other method */
        public DateBasedDeserializer<java.sql.Date> withDateFormat2(DateFormat df, String formatString) {
            return new SqlDateDeserializer(this, df, formatString);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public java.sql.Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            Date d = _parseDate(jp, ctxt);
            if (d == null) {
                return null;
            }
            return new java.sql.Date(d.getTime());
        }
    }

    public static class TimestampDeserializer extends DateBasedDeserializer<Timestamp> {
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer, com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public /* bridge */ /* synthetic */ JsonDeserializer createContextual(DeserializationContext x0, BeanProperty x1) throws JsonMappingException {
            return super.createContextual(x0, x1);
        }

        public TimestampDeserializer() {
            super(Timestamp.class);
        }

        public TimestampDeserializer(TimestampDeserializer src, DateFormat df, String formatString) {
            super(src, df, formatString);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer
        /* JADX INFO: renamed from: withDateFormat, reason: avoid collision after fix types in other method */
        public DateBasedDeserializer<Timestamp> withDateFormat2(DateFormat df, String formatString) {
            return new TimestampDeserializer(this, df, formatString);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Timestamp deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return new Timestamp(_parseDate(jp, ctxt).getTime());
        }
    }

    protected static class TimeZoneDeserializer extends FromStringDeserializer<TimeZone> {
        public TimeZoneDeserializer() {
            super(TimeZone.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public TimeZone _deserialize(String value, DeserializationContext ctxt) throws IOException {
            return TimeZone.getTimeZone(value);
        }
    }
}
