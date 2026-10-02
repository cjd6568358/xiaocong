package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.EnumMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EnumMapDeserializer extends StdDeserializer<EnumMap<?, ?>> implements ContextualDeserializer {
    protected final Class<?> _enumClass;
    protected JsonDeserializer<Enum<?>> _keyDeserializer;
    protected final JavaType _mapType;
    protected JsonDeserializer<Object> _valueDeserializer;

    /* JADX WARN: Multi-variable type inference failed */
    public EnumMapDeserializer(JavaType mapType, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> valueDeser) {
        super((Class<?>) EnumMap.class);
        this._mapType = mapType;
        this._enumClass = mapType.getKeyType().getRawClass();
        this._keyDeserializer = jsonDeserializer;
        this._valueDeserializer = valueDeser;
    }

    public EnumMapDeserializer withResolved(JsonDeserializer<?> keyDeserializer, JsonDeserializer<?> valueDeserializer) {
        return (keyDeserializer == this._keyDeserializer && valueDeserializer == this._valueDeserializer) ? this : new EnumMapDeserializer(this._mapType, keyDeserializer, valueDeserializer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.fasterxml.jackson.databind.JsonDeserializer] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.fasterxml.jackson.databind.deser.std.EnumMapDeserializer] */
    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        ?? CreateContextual;
        JsonDeserializer<Object> jsonDeserializerFindContextualValueDeserializer = this._keyDeserializer;
        if (jsonDeserializerFindContextualValueDeserializer == null) {
            jsonDeserializerFindContextualValueDeserializer = deserializationContext.findContextualValueDeserializer(this._mapType.getKeyType(), beanProperty);
        }
        Object obj = this._valueDeserializer;
        if (obj == null) {
            CreateContextual = deserializationContext.findContextualValueDeserializer(this._mapType.getContentType(), beanProperty);
        } else if (obj instanceof ContextualDeserializer) {
            CreateContextual = obj;
            CreateContextual = ((ContextualDeserializer) obj).createContextual(deserializationContext, beanProperty);
        }
        CreateContextual = obj;
        return withResolved(jsonDeserializerFindContextualValueDeserializer, CreateContextual);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public EnumMap<?, ?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
            throw ctxt.mappingException(EnumMap.class);
        }
        EnumMap<?, ?> enumMapConstructMap = constructMap();
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            Enum<?> key = this._keyDeserializer.deserialize(jp, ctxt);
            if (key == null) {
                if (!ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                    throw ctxt.weirdStringException(this._enumClass, "value not one of declared Enum instance names");
                }
                jp.nextToken();
                jp.skipChildren();
            } else {
                JsonToken t = jp.nextToken();
                Object value = t == JsonToken.VALUE_NULL ? null : this._valueDeserializer.deserialize(jp, ctxt);
                enumMapConstructMap.put(key, value);
            }
        }
        return enumMapConstructMap;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromObject(jp, ctxt);
    }

    private EnumMap<?, ?> constructMap() {
        return new EnumMap<>(this._enumClass);
    }
}
