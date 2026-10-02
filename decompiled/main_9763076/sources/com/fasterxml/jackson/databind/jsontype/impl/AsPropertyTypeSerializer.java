package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AsPropertyTypeSerializer extends AsArrayTypeSerializer {
    protected final String _typePropertyName;

    public AsPropertyTypeSerializer(TypeIdResolver idRes, BeanProperty property, String propName) {
        super(idRes, property);
        this._typePropertyName = propName;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public AsPropertyTypeSerializer forProperty(BeanProperty prop) {
        return this._property == prop ? this : new AsPropertyTypeSerializer(this._idResolver, prop, this._typePropertyName);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public String getPropertyName() {
        return this._typePropertyName;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer, com.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.PROPERTY;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForObject(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField(this._typePropertyName, idFromValue(value));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypePrefixForObject(Object value, JsonGenerator jgen, Class<?> type) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField(this._typePropertyName, idFromValueAndType(value, type));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeTypeSuffixForObject(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypePrefixForObject(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField(this._typePropertyName, typeId);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public void writeCustomTypeSuffixForObject(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeEndObject();
    }
}
