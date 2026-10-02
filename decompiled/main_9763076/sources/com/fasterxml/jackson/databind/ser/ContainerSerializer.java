package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ContainerSerializer<T> extends StdSerializer<T> {
    protected abstract ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer);

    public abstract JsonSerializer<?> getContentSerializer();

    public abstract JavaType getContentType();

    public abstract boolean hasSingleElement(T t);

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public abstract boolean isEmpty(T t);

    protected ContainerSerializer(Class<T> t) {
        super(t);
    }

    protected ContainerSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected ContainerSerializer(ContainerSerializer<?> src) {
        super(src._handledType, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ContainerSerializer<?> withValueTypeSerializer(TypeSerializer vts) {
        return vts == null ? this : _withValueTypeSerializer(vts);
    }
}
