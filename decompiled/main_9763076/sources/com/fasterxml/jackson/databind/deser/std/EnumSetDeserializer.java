package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.EnumSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EnumSetDeserializer extends StdDeserializer<EnumSet<?>> implements ContextualDeserializer {
    protected final Class<Enum> _enumClass;
    protected JsonDeserializer<Enum<?>> _enumDeserializer;
    protected final JavaType _enumType;

    /* JADX WARN: Multi-variable type inference failed */
    public EnumSetDeserializer(JavaType enumType, JsonDeserializer<?> jsonDeserializer) {
        super((Class<?>) EnumSet.class);
        this._enumType = enumType;
        this._enumClass = enumType.getRawClass();
        this._enumDeserializer = jsonDeserializer;
    }

    public EnumSetDeserializer withDeserializer(JsonDeserializer<?> deser) {
        return this._enumDeserializer == deser ? this : new EnumSetDeserializer(this._enumType, deser);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.fasterxml.jackson.databind.JsonDeserializer] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.fasterxml.jackson.databind.deser.std.EnumSetDeserializer] */
    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        ?? CreateContextual;
        Object obj = this._enumDeserializer;
        if (obj == null) {
            CreateContextual = deserializationContext.findContextualValueDeserializer(this._enumType, beanProperty);
        } else if (obj instanceof ContextualDeserializer) {
            CreateContextual = obj;
            CreateContextual = ((ContextualDeserializer) obj).createContextual(deserializationContext, beanProperty);
        }
        CreateContextual = obj;
        return withDeserializer(CreateContextual);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public EnumSet<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (!jp.isExpectedStartArrayToken()) {
            throw ctxt.mappingException(EnumSet.class);
        }
        EnumSet<?> enumSetConstructSet = constructSet();
        while (true) {
            JsonToken t = jp.nextToken();
            if (t != JsonToken.END_ARRAY) {
                if (t == JsonToken.VALUE_NULL) {
                    throw ctxt.mappingException(this._enumClass);
                }
                Enum<?> value = this._enumDeserializer.deserialize(jp, ctxt);
                if (value != null) {
                    enumSetConstructSet.add(value);
                }
            } else {
                return enumSetConstructSet;
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    private EnumSet constructSet() {
        return EnumSet.noneOf(this._enumClass);
    }
}
