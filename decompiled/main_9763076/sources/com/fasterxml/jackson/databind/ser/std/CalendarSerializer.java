package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class CalendarSerializer extends DateTimeSerializerBase<Calendar> {
    public static CalendarSerializer instance = new CalendarSerializer();

    public CalendarSerializer() {
        this(false, null);
    }

    public CalendarSerializer(boolean useTimestamp, DateFormat customFormat) {
        super(Calendar.class, useTimestamp, customFormat);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase
    /* JADX INFO: renamed from: withFormat */
    public DateTimeSerializerBase<Calendar> withFormat2(boolean timestamp, DateFormat customFormat) {
        return timestamp ? new CalendarSerializer(true, null) : new CalendarSerializer(false, customFormat);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase
    public long _timestamp(Calendar value) {
        if (value == null) {
            return 0L;
        }
        return value.getTimeInMillis();
    }

    @Override // com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Calendar value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (this._useTimestamp) {
            jgen.writeNumber(_timestamp(value));
        } else {
            if (this._customFormat != null) {
                synchronized (this._customFormat) {
                    jgen.writeString(this._customFormat.format(value));
                }
                return;
            }
            provider.defaultSerializeDateValue(value.getTime(), jgen);
        }
    }
}
