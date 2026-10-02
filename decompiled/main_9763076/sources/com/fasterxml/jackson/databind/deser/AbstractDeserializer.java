package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AbstractDeserializer extends JsonDeserializer<Object> {
    protected final boolean _acceptBoolean;
    protected final boolean _acceptDouble;
    protected final boolean _acceptInt;
    protected final boolean _acceptString;
    protected final Map<String, SettableBeanProperty> _backRefProperties;
    protected final JavaType _baseType;
    protected final ObjectIdReader _objectIdReader;

    public AbstractDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, Map<String, SettableBeanProperty> backRefProps) {
        this._baseType = beanDesc.getType();
        this._objectIdReader = builder.getObjectIdReader();
        this._backRefProperties = backRefProps;
        Class<?> cls = this._baseType.getRawClass();
        this._acceptString = cls.isAssignableFrom(String.class);
        this._acceptBoolean = cls == Boolean.TYPE || cls.isAssignableFrom(Boolean.class);
        this._acceptInt = cls == Integer.TYPE || cls.isAssignableFrom(Integer.class);
        this._acceptDouble = cls == Double.TYPE || cls.isAssignableFrom(Double.class);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    public SettableBeanProperty findBackReference(String logicalName) {
        if (this._backRefProperties == null) {
            return null;
        }
        return this._backRefProperties.get(logicalName);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        JsonToken t;
        if (this._objectIdReader != null && (t = jp.getCurrentToken()) != null && t.isScalarValue()) {
            return _deserializeFromObjectId(jp, ctxt);
        }
        Object result = _deserializeIfNatural(jp, ctxt);
        return result == null ? typeDeserializer.deserializeTypedFromObject(jp, ctxt) : result;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        throw ctxt.instantiationException(this._baseType.getRawClass(), "abstract types either need to be mapped to concrete types, have custom deserializer, or be instantiated with additional type information");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected Object _deserializeIfNatural(JsonParser jp, DeserializationContext ctxt) throws IOException {
        switch (jp.getCurrentToken()) {
            case VALUE_STRING:
                if (this._acceptString) {
                    return jp.getText();
                }
                return null;
            case VALUE_NUMBER_INT:
                if (this._acceptInt) {
                    return Integer.valueOf(jp.getIntValue());
                }
                return null;
            case VALUE_NUMBER_FLOAT:
                if (this._acceptDouble) {
                    return Double.valueOf(jp.getDoubleValue());
                }
                return null;
            case VALUE_TRUE:
                if (this._acceptBoolean) {
                    return Boolean.TRUE;
                }
                return null;
            case VALUE_FALSE:
                if (this._acceptBoolean) {
                    return Boolean.FALSE;
                }
                return null;
            default:
                return null;
        }
    }

    protected Object _deserializeFromObjectId(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Object id = this._objectIdReader.deserializer.deserialize(jp, ctxt);
        ReadableObjectId roid = ctxt.findObjectId(id, this._objectIdReader.generator);
        Object pojo = roid.item;
        if (pojo == null) {
            throw new IllegalStateException("Could not resolve Object Id [" + id + "] -- unresolved forward-reference?");
        }
        return pojo;
    }
}
