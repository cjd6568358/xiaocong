package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ArraySerializerBase<T> extends ContainerSerializer<T> {
    protected final BeanProperty _property;

    protected abstract void serializeContents(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    protected ArraySerializerBase(Class<T> cls) {
        super(cls);
        this._property = null;
    }

    protected ArraySerializerBase(Class<T> cls, BeanProperty property) {
        super(cls);
        this._property = property;
    }

    protected ArraySerializerBase(ArraySerializerBase<?> src) {
        super(src._handledType, false);
        this._property = src._property;
    }

    protected ArraySerializerBase(ArraySerializerBase<?> src, BeanProperty property) {
        super(src._handledType, false);
        this._property = property;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public final void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && hasSingleElement(value)) {
            serializeContents(value, jgen, provider);
            return;
        }
        jgen.writeStartArray();
        serializeContents(value, jgen, provider);
        jgen.writeEndArray();
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public final void serializeWithType(T value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForArray(value, jgen);
        serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForArray(value, jgen);
    }
}
