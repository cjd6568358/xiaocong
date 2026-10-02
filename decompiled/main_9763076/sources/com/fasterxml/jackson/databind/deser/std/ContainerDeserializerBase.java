package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ContainerDeserializerBase<T> extends StdDeserializer<T> {
    public abstract JsonDeserializer<Object> getContentDeserializer();

    public abstract JavaType getContentType();

    protected ContainerDeserializerBase(Class<?> selfType) {
        super(selfType);
    }
}
