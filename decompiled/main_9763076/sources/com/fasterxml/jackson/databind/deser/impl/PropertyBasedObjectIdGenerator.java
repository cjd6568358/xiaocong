package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PropertyBasedObjectIdGenerator extends ObjectIdGenerators.PropertyGenerator {
    public PropertyBasedObjectIdGenerator(Class<?> scope) {
        super(scope);
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
    public Object generateId(Object forPojo) {
        throw new UnsupportedOperationException();
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<Object> forScope(Class<?> scope) {
        return scope == this._scope ? this : new PropertyBasedObjectIdGenerator(scope);
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<Object> newForSerialization(Object context) {
        return this;
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator.IdKey key(Object key) {
        return new ObjectIdGenerator.IdKey(getClass(), this._scope, key);
    }
}
