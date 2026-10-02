package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class WritableObjectId {
    public final ObjectIdGenerator<?> generator;
    public Object id;
    public JsonSerializer<Object> serializer;

    public WritableObjectId(ObjectIdGenerator<?> generator) {
        this.generator = generator;
    }
}
