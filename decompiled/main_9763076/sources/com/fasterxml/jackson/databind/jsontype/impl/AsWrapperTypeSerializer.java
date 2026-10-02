package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AsWrapperTypeSerializer extends TypeSerializerBase {
    public AsWrapperTypeSerializer(TypeIdResolver idRes, BeanProperty property) {
        super(idRes, property);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public AsWrapperTypeSerializer forProperty(BeanProperty prop) {
        return this._property == prop ? this : new AsWrapperTypeSerializer(this._idResolver, prop);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_OBJECT;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForObject(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectFieldStart(idFromValue(value));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForObject(Object value, JsonGenerator jgen, Class<?> type) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectFieldStart(idFromValueAndType(value, type));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForArray(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartObject();
        jgen.writeArrayFieldStart(idFromValue(value));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForArray(Object value, JsonGenerator jgen, Class<?> type) throws IOException {
        jgen.writeStartObject();
        jgen.writeArrayFieldStart(idFromValueAndType(value, type));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForScalar(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartObject();
        jgen.writeFieldName(idFromValue(value));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForScalar(Object value, JsonGenerator jgen, Class<?> type) throws IOException {
        jgen.writeStartObject();
        jgen.writeFieldName(idFromValueAndType(value, type));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypeSuffixForObject(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeEndObject();
        jgen.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypeSuffixForArray(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypeSuffixForScalar(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypePrefixForObject(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectFieldStart(typeId);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypePrefixForArray(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeStartObject();
        jgen.writeArrayFieldStart(typeId);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypePrefixForScalar(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeStartObject();
        jgen.writeFieldName(typeId);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypeSuffixForObject(Object value, JsonGenerator jgen, String typeId) throws IOException {
        writeTypeSuffixForObject(value, jgen);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypeSuffixForArray(Object value, JsonGenerator jgen, String typeId) throws IOException {
        writeTypeSuffixForArray(value, jgen);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypeSuffixForScalar(Object value, JsonGenerator jgen, String typeId) throws IOException {
        writeTypeSuffixForScalar(value, jgen);
    }
}
