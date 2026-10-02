package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DateTimeSerializerBase<T> extends StdScalarSerializer<T> implements ContextualSerializer {
    protected final DateFormat _customFormat;
    protected final boolean _useTimestamp;

    protected abstract long _timestamp(T t);

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public abstract void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    /* JADX INFO: renamed from: withFormat */
    public abstract DateTimeSerializerBase<T> withFormat2(boolean z, DateFormat dateFormat);

    protected DateTimeSerializerBase(Class<T> type, boolean useTimestamp, DateFormat customFormat) {
        super(type);
        this._useTimestamp = useTimestamp;
        this._customFormat = customFormat;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        JsonFormat.Value format;
        DateFormat df;
        if (property != null && (format = prov.getAnnotationIntrospector().findFormat(property.getMember())) != null) {
            if (format.getShape().isNumeric()) {
                return withFormat2(true, null);
            }
            TimeZone tz = format.getTimeZone();
            String pattern = format.getPattern();
            if (pattern.length() > 0) {
                Locale loc = format.getLocale();
                if (loc == null) {
                    loc = prov.getLocale();
                }
                SimpleDateFormat df2 = new SimpleDateFormat(pattern, loc);
                if (tz == null) {
                    tz = prov.getTimeZone();
                }
                df2.setTimeZone(tz);
                return withFormat2(false, df2);
            }
            if (tz != null) {
                DateFormat df3 = prov.getConfig().getDateFormat();
                if (df3.getClass() == StdDateFormat.class) {
                    df = StdDateFormat.getISO8601Format(tz);
                } else {
                    df = (DateFormat) df3.clone();
                    df.setTimeZone(tz);
                }
                return withFormat2(false, df);
            }
            return this;
        }
        return this;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(T value) {
        return value == null || _timestamp(value) == 0;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        boolean asNumber = this._useTimestamp;
        if (!asNumber && this._customFormat == null) {
            asNumber = provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return createSchemaNode(asNumber ? "number" : SettingsContentProvider.STRING_TYPE, true);
    }
}
