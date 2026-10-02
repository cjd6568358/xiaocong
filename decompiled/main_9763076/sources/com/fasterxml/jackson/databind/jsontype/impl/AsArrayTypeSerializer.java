package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AsArrayTypeSerializer extends TypeSerializerBase {
    public AsArrayTypeSerializer(TypeIdResolver idRes, BeanProperty property) {
        super(idRes, property);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public AsArrayTypeSerializer forProperty(BeanProperty prop) {
        return this._property == prop ? this : new AsArrayTypeSerializer(this._idResolver, prop);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_ARRAY;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForObject(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(idFromValue(value));
        jgen.writeStartObject();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForObject(Object value, JsonGenerator jgen, Class<?> type) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(idFromValueAndType(value, type));
        jgen.writeStartObject();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForArray(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(idFromValue(value));
        jgen.writeStartArray();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForArray(Object value, JsonGenerator jgen, Class<?> type) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(idFromValueAndType(value, type));
        jgen.writeStartArray();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForScalar(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(idFromValue(value));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForScalar(Object value, JsonGenerator jgen, Class<?> type) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(idFromValueAndType(value, type));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypeSuffixForObject(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeEndObject();
        jgen.writeEndArray();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypeSuffixForArray(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeEndArray();
        jgen.writeEndArray();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypeSuffixForScalar(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeEndArray();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypePrefixForObject(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(typeId);
        jgen.writeStartObject();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypePrefixForArray(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(typeId);
        jgen.writeStartArray();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypePrefixForScalar(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(typeId);
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
