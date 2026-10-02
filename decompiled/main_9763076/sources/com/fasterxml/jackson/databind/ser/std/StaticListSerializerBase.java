package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.lang.reflect.Type;
import java.util.Collection;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class StaticListSerializerBase<T extends Collection<?>> extends StdSerializer<T> {
    protected abstract JsonNode contentSchema();

    protected StaticListSerializerBase(Class<?> cls) {
        super(cls, false);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(T value) {
        return value == null || value.size() == 0;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        ObjectNode o = createSchemaNode("array", true);
        o.put("items", contentSchema());
        return o;
    }
}
