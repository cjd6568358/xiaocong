package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AsPropertyTypeDeserializer extends AsArrayTypeDeserializer {
    public AsPropertyTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public AsPropertyTypeDeserializer(AsPropertyTypeDeserializer src, BeanProperty property) {
        super(src, property);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer, com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public TypeDeserializer forProperty(BeanProperty prop) {
        return prop == this._property ? this : new AsPropertyTypeDeserializer(this, prop);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer, com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.PROPERTY;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Object deserializeTypedFromObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        } else {
            if (t == JsonToken.START_ARRAY) {
                return _deserializeTypedUsingDefaultImpl(jp, ctxt, null);
            }
            if (t != JsonToken.FIELD_NAME) {
                return _deserializeTypedUsingDefaultImpl(jp, ctxt, null);
            }
        }
        TokenBuffer tb = null;
        while (t == JsonToken.FIELD_NAME) {
            String name = jp.getCurrentName();
            jp.nextToken();
            if (this._typePropertyName.equals(name)) {
                return _deserializeTypedForId(jp, ctxt, tb);
            }
            if (tb == null) {
                tb = new TokenBuffer(null);
            }
            tb.writeFieldName(name);
            tb.copyCurrentStructure(jp);
            t = jp.nextToken();
        }
        return _deserializeTypedUsingDefaultImpl(jp, ctxt, tb);
    }

    protected final Object _deserializeTypedForId(JsonParser jp, DeserializationContext ctxt, TokenBuffer tb) throws IOException {
        String typeId = jp.getText();
        JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
        if (this._typeIdVisible) {
            if (tb == null) {
                tb = new TokenBuffer(null);
            }
            tb.writeFieldName(jp.getCurrentName());
            tb.writeString(typeId);
        }
        if (tb != null) {
            jp = JsonParserSequence.createFlattened(tb.asParser(jp), jp);
        }
        jp.nextToken();
        return deser.deserialize(jp, ctxt);
    }

    protected Object _deserializeTypedUsingDefaultImpl(JsonParser jp, DeserializationContext ctxt, TokenBuffer tb) throws IOException {
        if (this._defaultImpl != null) {
            JsonDeserializer<Object> deser = _findDefaultImplDeserializer(ctxt);
            if (tb != null) {
                tb.writeEndObject();
                jp = tb.asParser(jp);
                jp.nextToken();
            }
            return deser.deserialize(jp, ctxt);
        }
        Object result = _deserializeIfNatural(jp, ctxt);
        if (result == null) {
            if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
                return super.deserializeTypedFromAny(jp, ctxt);
            }
            throw ctxt.wrongTokenException(jp, JsonToken.FIELD_NAME, "missing property '" + this._typePropertyName + "' that is to contain type id  (for class " + baseTypeName() + ")");
        }
        return result;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Object deserializeTypedFromAny(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return jp.getCurrentToken() == JsonToken.START_ARRAY ? super.deserializeTypedFromArray(jp, ctxt) : deserializeTypedFromObject(jp, ctxt);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected Object _deserializeIfNatural(JsonParser jp, DeserializationContext ctxt) throws IOException {
        switch (jp.getCurrentToken()) {
            case VALUE_STRING:
                if (this._baseType.getRawClass().isAssignableFrom(String.class)) {
                    return jp.getText();
                }
                return null;
            case VALUE_NUMBER_INT:
                if (this._baseType.getRawClass().isAssignableFrom(Integer.class)) {
                    return Integer.valueOf(jp.getIntValue());
                }
                return null;
            case VALUE_NUMBER_FLOAT:
                if (this._baseType.getRawClass().isAssignableFrom(Double.class)) {
                    return Double.valueOf(jp.getDoubleValue());
                }
                return null;
            case VALUE_TRUE:
                if (this._baseType.getRawClass().isAssignableFrom(Boolean.class)) {
                    return Boolean.TRUE;
                }
                return null;
            case VALUE_FALSE:
                if (this._baseType.getRawClass().isAssignableFrom(Boolean.class)) {
                    return Boolean.FALSE;
                }
                return null;
            default:
                return null;
        }
    }
}
