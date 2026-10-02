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
public class AsArrayTypeDeserializer extends TypeDeserializerBase {
    public AsArrayTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public AsArrayTypeDeserializer(AsArrayTypeDeserializer src, BeanProperty property) {
        super(src, property);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public TypeDeserializer forProperty(BeanProperty prop) {
        return prop == this._property ? this : new AsArrayTypeDeserializer(this, prop);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_ARRAY;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Object deserializeTypedFromArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return _deserialize(jp, ctxt);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Object deserializeTypedFromObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return _deserialize(jp, ctxt);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Object deserializeTypedFromScalar(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return _deserialize(jp, ctxt);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Object deserializeTypedFromAny(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return _deserialize(jp, ctxt);
    }

    private final Object _deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        boolean hadStartArray = jp.isExpectedStartArrayToken();
        String typeId = _locateTypeId(jp, ctxt);
        JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
        if (this._typeIdVisible && jp.getCurrentToken() == JsonToken.START_OBJECT) {
            TokenBuffer tb = new TokenBuffer(null);
            tb.writeStartObject();
            tb.writeFieldName(this._typePropertyName);
            tb.writeString(typeId);
            jp = JsonParserSequence.createFlattened(tb.asParser(jp), jp);
            jp.nextToken();
        }
        Object value = deser.deserialize(jp, ctxt);
        if (hadStartArray && jp.nextToken() != JsonToken.END_ARRAY) {
            throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "expected closing END_ARRAY after type information and deserialized value");
        }
        return value;
    }

    protected final String _locateTypeId(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (!jp.isExpectedStartArrayToken()) {
            if (this._defaultImpl != null) {
                return this._idResolver.idFromBaseType();
            }
            throw ctxt.wrongTokenException(jp, JsonToken.START_ARRAY, "need JSON Array to contain As.WRAPPER_ARRAY type information for class " + baseTypeName());
        }
        if (jp.nextToken() != JsonToken.VALUE_STRING) {
            if (this._defaultImpl != null) {
                return this._idResolver.idFromBaseType();
            }
            throw ctxt.wrongTokenException(jp, JsonToken.VALUE_STRING, "need JSON String that contains type id (for subtype of " + baseTypeName() + ")");
        }
        String text = jp.getText();
        jp.nextToken();
        return text;
    }
}
