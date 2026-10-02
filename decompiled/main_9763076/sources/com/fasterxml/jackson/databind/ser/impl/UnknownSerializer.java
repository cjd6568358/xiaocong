package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UnknownSerializer extends StdSerializer<Object> {
    public UnknownSerializer() {
        super(Object.class);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (provider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            failForEmpty(value);
        }
        jgen.writeStartObject();
        jgen.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public final void serializeWithType(Object value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        if (provider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            failForEmpty(value);
        }
        typeSer.writeTypePrefixForObject(value, jgen);
        typeSer.writeTypeSuffixForObject(value, jgen);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return null;
    }

    protected void failForEmpty(Object value) throws JsonMappingException {
        throw new JsonMappingException("No serializer found for class " + value.getClass().getName() + " and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationConfig.SerializationFeature.FAIL_ON_EMPTY_BEANS) )");
    }
}
