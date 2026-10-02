package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class FromStringDeserializer<T> extends StdScalarDeserializer<T> {
    protected abstract T _deserialize(String str, DeserializationContext deserializationContext) throws IOException;

    protected FromStringDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public final T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
            String strTrim = jsonParser.getText().trim();
            if (strTrim.length() == 0) {
                return null;
            }
            try {
                T t_deserialize = _deserialize(strTrim, deserializationContext);
                if (t_deserialize != null) {
                    return t_deserialize;
                }
            } catch (IllegalArgumentException e) {
            }
            throw deserializationContext.weirdStringException(this._valueClass, "not a valid textual representation");
        }
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_EMBEDDED_OBJECT) {
            T t = (T) jsonParser.getEmbeddedObject();
            if (t != null) {
                return this._valueClass.isAssignableFrom(t.getClass()) ? t : _deserializeEmbedded(t, deserializationContext);
            }
            return null;
        }
        throw deserializationContext.mappingException(this._valueClass);
    }

    protected T _deserializeEmbedded(Object ob, DeserializationContext ctxt) throws IOException {
        throw ctxt.mappingException("Don't know how to convert embedded Object of type " + ob.getClass().getName() + " into " + this._valueClass.getName());
    }
}
