package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ObjectIdReader {
    public final JsonDeserializer<Object> deserializer;
    public final ObjectIdGenerator<?> generator;
    public final SettableBeanProperty idProperty;
    public final JavaType idType;
    public final String propertyName;

    protected ObjectIdReader(JavaType t, String propName, ObjectIdGenerator<?> gen, JsonDeserializer<?> deser, SettableBeanProperty idProp) {
        this.idType = t;
        this.propertyName = propName;
        this.generator = gen;
        this.deserializer = deser;
        this.idProperty = idProp;
    }

    public static ObjectIdReader construct(JavaType idType, String propName, ObjectIdGenerator<?> generator, JsonDeserializer<?> deser, SettableBeanProperty idProp) {
        return new ObjectIdReader(idType, propName, generator, deser, idProp);
    }
}
