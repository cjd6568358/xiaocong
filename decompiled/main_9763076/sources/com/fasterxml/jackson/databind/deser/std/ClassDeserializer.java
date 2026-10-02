package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class ClassDeserializer extends StdScalarDeserializer<Class<?>> {
    public ClassDeserializer() {
        super((Class<?>) Class.class);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Class<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken curr = jp.getCurrentToken();
        if (curr == JsonToken.VALUE_STRING) {
            String className = jp.getText().trim();
            try {
                return ctxt.findClass(className);
            } catch (Exception e) {
                throw ctxt.instantiationException(this._valueClass, ClassUtil.getRootCause(e));
            }
        }
        throw ctxt.mappingException(this._valueClass, curr);
    }
}
