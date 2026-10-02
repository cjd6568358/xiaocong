package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class TypeDeserializerBase extends TypeDeserializer {
    protected final JavaType _baseType;
    protected final JavaType _defaultImpl;
    protected JsonDeserializer<Object> _defaultImplDeserializer;
    protected final HashMap<String, JsonDeserializer<Object>> _deserializers;
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;
    protected final boolean _typeIdVisible;
    protected final String _typePropertyName;

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public abstract TypeDeserializer forProperty(BeanProperty beanProperty);

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public abstract JsonTypeInfo.As getTypeInclusion();

    protected TypeDeserializerBase(JavaType baseType, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, Class<?> defaultImpl) {
        this._baseType = baseType;
        this._idResolver = idRes;
        this._typePropertyName = typePropertyName;
        this._typeIdVisible = typeIdVisible;
        this._deserializers = new HashMap<>();
        if (defaultImpl == null) {
            this._defaultImpl = null;
        } else {
            this._defaultImpl = baseType.forcedNarrowBy(defaultImpl);
        }
        this._property = null;
    }

    protected TypeDeserializerBase(TypeDeserializerBase src, BeanProperty property) {
        this._baseType = src._baseType;
        this._idResolver = src._idResolver;
        this._typePropertyName = src._typePropertyName;
        this._typeIdVisible = src._typeIdVisible;
        this._deserializers = src._deserializers;
        this._defaultImpl = src._defaultImpl;
        this._defaultImplDeserializer = src._defaultImplDeserializer;
        this._property = property;
    }

    public String baseTypeName() {
        return this._baseType.getRawClass().getName();
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public final String getPropertyName() {
        return this._typePropertyName;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Class<?> getDefaultImpl() {
        if (this._defaultImpl == null) {
            return null;
        }
        return this._defaultImpl.getRawClass();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[').append(getClass().getName());
        sb.append("; base-type:").append(this._baseType);
        sb.append("; id-resolver: ").append(this._idResolver);
        sb.append(']');
        return sb.toString();
    }

    protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext ctxt, String typeId) throws IOException {
        JsonDeserializer<Object> deser;
        synchronized (this._deserializers) {
            deser = this._deserializers.get(typeId);
            if (deser == null) {
                JavaType type = this._idResolver.typeFromId(typeId);
                if (type == null) {
                    if (this._defaultImpl == null) {
                        throw ctxt.unknownTypeException(this._baseType, typeId);
                    }
                    deser = _findDefaultImplDeserializer(ctxt);
                } else {
                    if (this._baseType != null && this._baseType.getClass() == type.getClass()) {
                        type = this._baseType.narrowBy(type.getRawClass());
                    }
                    deser = ctxt.findContextualValueDeserializer(type, this._property);
                }
                this._deserializers.put(typeId, deser);
            }
        }
        return deser;
    }

    protected final JsonDeserializer<Object> _findDefaultImplDeserializer(DeserializationContext ctxt) throws IOException {
        JsonDeserializer<Object> jsonDeserializer;
        if (this._defaultImpl == null) {
            return null;
        }
        synchronized (this._defaultImpl) {
            if (this._defaultImplDeserializer == null) {
                this._defaultImplDeserializer = ctxt.findContextualValueDeserializer(this._defaultImpl, this._property);
            }
            jsonDeserializer = this._defaultImplDeserializer;
        }
        return jsonDeserializer;
    }
}
