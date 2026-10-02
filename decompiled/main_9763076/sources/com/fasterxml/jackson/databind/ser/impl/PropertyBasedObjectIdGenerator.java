package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PropertyBasedObjectIdGenerator extends ObjectIdGenerators.PropertyGenerator {
    protected final BeanPropertyWriter _property;

    public PropertyBasedObjectIdGenerator(ObjectIdInfo oid, BeanPropertyWriter prop) {
        this(oid.getScope(), prop);
    }

    protected PropertyBasedObjectIdGenerator(Class<?> scope, BeanPropertyWriter prop) {
        super(scope);
        this._property = prop;
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
    public Object generateId(Object forPojo) {
        try {
            return this._property.get(forPojo);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new IllegalStateException("Problem accessing property '" + this._property.getName() + "': " + e2.getMessage(), e2);
        }
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<Object> forScope(Class<?> scope) {
        return scope == this._scope ? this : new PropertyBasedObjectIdGenerator(scope, this._property);
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
