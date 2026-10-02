package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public final class BooleanSerializer extends NonTypedScalarSerializerBase<Boolean> {
    final boolean _forPrimitive;

    public BooleanSerializer(boolean forPrimitive) {
        super(Boolean.class);
        this._forPrimitive = forPrimitive;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Boolean value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeBoolean(value.booleanValue());
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        return createSchemaNode(SettingsContentProvider.BOOLEAN_TYPE, !this._forPrimitive);
    }
}
