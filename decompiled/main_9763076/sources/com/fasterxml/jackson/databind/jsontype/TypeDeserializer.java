package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class TypeDeserializer {
    public abstract Object deserializeTypedFromAny(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

    public abstract Object deserializeTypedFromArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

    public abstract Object deserializeTypedFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

    public abstract Object deserializeTypedFromScalar(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

    public abstract TypeDeserializer forProperty(BeanProperty beanProperty);

    public abstract Class<?> getDefaultImpl();

    public abstract String getPropertyName();

    public abstract TypeIdResolver getTypeIdResolver();

    public abstract JsonTypeInfo.As getTypeInclusion();
}
