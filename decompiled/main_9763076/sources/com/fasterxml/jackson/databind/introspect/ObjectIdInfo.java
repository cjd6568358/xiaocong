package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ObjectIdInfo {
    protected final Class<? extends ObjectIdGenerator<?>> _generator;
    protected final String _propertyName;
    protected final Class<?> _scope;

    public ObjectIdInfo(String prop, Class<?> scope, Class<? extends ObjectIdGenerator<?>> gen) {
        this._propertyName = prop;
        this._generator = gen;
        this._scope = scope;
    }

    public String getPropertyName() {
        return this._propertyName;
    }

    public Class<?> getScope() {
        return this._scope;
    }

    public Class<? extends ObjectIdGenerator<?>> getGeneratorType() {
        return this._generator;
    }
}
