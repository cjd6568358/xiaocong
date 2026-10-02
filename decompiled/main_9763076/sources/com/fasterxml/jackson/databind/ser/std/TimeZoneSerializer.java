package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TimeZoneSerializer extends StdScalarSerializer<TimeZone> {
    public static final TimeZoneSerializer instance = new TimeZoneSerializer();

    public TimeZoneSerializer() {
        super(TimeZone.class);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(TimeZone value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.getID());
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(TimeZone value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForScalar(value, jgen, TimeZone.class);
        serialize(value, jgen, provider);
        typeSer.writeTypeSuffixForScalar(value, jgen);
    }
}
