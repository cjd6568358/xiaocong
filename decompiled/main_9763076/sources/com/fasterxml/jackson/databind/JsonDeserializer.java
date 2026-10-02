package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Collection;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class JsonDeserializer<T> {
    public abstract T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

    public T deserialize(JsonParser jp, DeserializationContext ctxt, T intoValue) throws IOException {
        throw new UnsupportedOperationException("Can not update object of type " + intoValue.getClass().getName());
    }

    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jp, ctxt);
    }

    public JsonDeserializer<T> unwrappingDeserializer(NameTransformer unwrapper) {
        return this;
    }

    public T getNullValue() {
        return null;
    }

    public T getEmptyValue() {
        return getNullValue();
    }

    public Collection<Object> getKnownPropertyNames() {
        return null;
    }

    public boolean isCachable() {
        return false;
    }

    public ObjectIdReader getObjectIdReader() {
        return null;
    }

    public static abstract class None extends JsonDeserializer<Object> {
        private None() {
        }
    }
}
