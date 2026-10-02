package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class TypeSerializerBase extends TypeSerializer {
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public abstract JsonTypeInfo.As getTypeInclusion();

    protected TypeSerializerBase(TypeIdResolver idRes, BeanProperty property) {
        this._idResolver = idRes;
        this._property = property;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public String getPropertyName() {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }

    protected String idFromValue(Object value) {
        return this._idResolver.idFromValue(value);
    }

    protected String idFromValueAndType(Object value, Class<?> type) {
        return this._idResolver.idFromValueAndType(value, type);
    }
}
