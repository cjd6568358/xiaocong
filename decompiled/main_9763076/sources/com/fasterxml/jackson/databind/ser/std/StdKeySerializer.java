package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StdKeySerializer extends StdSerializer<Object> {
    static final StdKeySerializer instace = new StdKeySerializer();

    public StdKeySerializer() {
        super(Object.class);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value instanceof Date) {
            provider.defaultSerializeDateKey((Date) value, jgen);
        } else {
            jgen.writeFieldName(value.toString());
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return createSchemaNode(SettingsContentProvider.STRING_TYPE);
    }
}
