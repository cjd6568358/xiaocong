package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class JsonSerializer<T> {

    public static abstract class None extends JsonSerializer<Object> {
    }

    public abstract void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    public JsonSerializer<T> unwrappingSerializer(NameTransformer unwrapper) {
        return this;
    }

    public boolean isUnwrappingSerializer() {
        return false;
    }

    public void serializeWithType(T value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        Class clsHandledType = handledType();
        if (clsHandledType == null) {
            clsHandledType = value.getClass();
        }
        throw new UnsupportedOperationException("Type id handling not implemented for type " + clsHandledType.getName());
    }

    public Class<T> handledType() {
        return null;
    }

    public boolean isEmpty(T value) {
        return value == null;
    }

    public boolean usesObjectId() {
        return false;
    }
}
