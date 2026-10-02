package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class ObjectArrayDeserializer extends ContainerDeserializerBase<Object[]> implements ContextualDeserializer {
    protected final ArrayType _arrayType;
    protected final Class<?> _elementClass;
    protected JsonDeserializer<Object> _elementDeserializer;
    protected final TypeDeserializer _elementTypeDeserializer;
    protected final boolean _untyped;

    public ObjectArrayDeserializer(ArrayType arrayType, JsonDeserializer<Object> elemDeser, TypeDeserializer elemTypeDeser) {
        super(Object[].class);
        this._arrayType = arrayType;
        this._elementClass = arrayType.getContentType().getRawClass();
        this._untyped = this._elementClass == Object.class;
        this._elementDeserializer = elemDeser;
        this._elementTypeDeserializer = elemTypeDeser;
    }

    public ObjectArrayDeserializer withDeserializer(TypeDeserializer elemTypeDeser, JsonDeserializer<?> elemDeser) {
        return (elemDeser == this._elementDeserializer && elemTypeDeser == this._elementTypeDeserializer) ? this : new ObjectArrayDeserializer(this._arrayType, elemDeser, elemTypeDeser);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.fasterxml.jackson.databind.JsonDeserializer] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer] */
    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        ?? CreateContextual;
        Object obj = this._elementDeserializer;
        if (obj == null) {
            CreateContextual = deserializationContext.findContextualValueDeserializer(this._arrayType.getContentType(), beanProperty);
        } else if (obj instanceof ContextualDeserializer) {
            CreateContextual = obj;
            CreateContextual = ((ContextualDeserializer) obj).createContextual(deserializationContext, beanProperty);
        }
        CreateContextual = obj;
        TypeDeserializer typeDeserializerForProperty = this._elementTypeDeserializer;
        if (typeDeserializerForProperty != null) {
            typeDeserializerForProperty = typeDeserializerForProperty.forProperty(beanProperty);
        }
        return withDeserializer(typeDeserializerForProperty, CreateContextual);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public JavaType getContentType() {
        return this._arrayType.getContentType();
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._elementDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object[] deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Object[] result;
        Object objDeserializeWithType;
        if (!jp.isExpectedStartArrayToken()) {
            return handleNonArray(jp, ctxt);
        }
        ObjectBuffer buffer = ctxt.leaseObjectBuffer();
        Object[] chunk = buffer.resetAndStart();
        int ix = 0;
        TypeDeserializer typeDeser = this._elementTypeDeserializer;
        while (true) {
            JsonToken t = jp.nextToken();
            if (t == JsonToken.END_ARRAY) {
                break;
            }
            if (t == JsonToken.VALUE_NULL) {
                objDeserializeWithType = null;
            } else if (typeDeser == null) {
                objDeserializeWithType = this._elementDeserializer.deserialize(jp, ctxt);
            } else {
                objDeserializeWithType = this._elementDeserializer.deserializeWithType(jp, ctxt, typeDeser);
            }
            if (ix >= chunk.length) {
                chunk = buffer.appendCompletedChunk(chunk);
                ix = 0;
            }
            chunk[ix] = objDeserializeWithType;
            ix++;
        }
        if (this._untyped) {
            result = buffer.completeAndClearBuffer(chunk, ix);
        } else {
            result = buffer.completeAndClearBuffer(chunk, ix, this._elementClass);
        }
        ctxt.returnObjectBuffer(buffer);
        return result;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object[] deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return (Object[]) typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    protected Byte[] deserializeFromBase64(JsonParser jp, DeserializationContext ctxt) throws IOException {
        byte[] b = jp.getBinaryValue(ctxt.getBase64Variant());
        Byte[] result = new Byte[b.length];
        int len = b.length;
        for (int i = 0; i < len; i++) {
            result[i] = Byte.valueOf(b[i]);
        }
        return result;
    }

    private final Object[] handleNonArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Object objDeserializeWithType;
        Object[] result;
        if (jp.getCurrentToken() == JsonToken.VALUE_STRING && ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
            String str = jp.getText();
            if (str.length() == 0) {
                return null;
            }
        }
        if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            if (jp.getCurrentToken() == JsonToken.VALUE_STRING && this._elementClass == Byte.class) {
                return deserializeFromBase64(jp, ctxt);
            }
            throw ctxt.mappingException(this._arrayType.getRawClass());
        }
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NULL) {
            objDeserializeWithType = null;
        } else if (this._elementTypeDeserializer == null) {
            objDeserializeWithType = this._elementDeserializer.deserialize(jp, ctxt);
        } else {
            objDeserializeWithType = this._elementDeserializer.deserializeWithType(jp, ctxt, this._elementTypeDeserializer);
        }
        if (this._untyped) {
            result = new Object[1];
        } else {
            result = (Object[]) Array.newInstance(this._elementClass, 1);
        }
        result[0] = objDeserializeWithType;
        return result;
    }
}
