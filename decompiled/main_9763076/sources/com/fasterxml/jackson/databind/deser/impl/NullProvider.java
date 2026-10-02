package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class NullProvider {
    private final boolean _isPrimitive;
    private final Object _nullValue;
    private final Class<?> _rawType;

    public NullProvider(JavaType type, Object nullValue) {
        this._nullValue = nullValue;
        this._isPrimitive = type.isPrimitive();
        this._rawType = type.getRawClass();
    }

    public Object nullValue(DeserializationContext ctxt) throws JsonProcessingException {
        if (this._isPrimitive && ctxt.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
            throw ctxt.mappingException("Can not map JSON null into type " + this._rawType.getName() + " (set DeserializationConfig.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES to 'false' to allow)");
        }
        return this._nullValue;
    }
}
