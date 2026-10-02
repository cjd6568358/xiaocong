package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ObjectIdWriter {
    public final ObjectIdGenerator<?> generator;
    public final JavaType idType;
    public final SerializedString propertyName;
    public final JsonSerializer<Object> serializer;

    protected ObjectIdWriter(JavaType t, SerializedString propName, ObjectIdGenerator<?> gen, JsonSerializer<?> ser) {
        this.idType = t;
        this.propertyName = propName;
        this.generator = gen;
        this.serializer = ser;
    }

    public static ObjectIdWriter construct(JavaType idType, String propName, ObjectIdGenerator<?> generator) {
        SerializedString serName = propName == null ? null : new SerializedString(propName);
        return new ObjectIdWriter(idType, serName, generator, null);
    }

    public ObjectIdWriter withSerializer(JsonSerializer<?> ser) {
        return new ObjectIdWriter(this.idType, this.propertyName, this.generator, ser);
    }
}
