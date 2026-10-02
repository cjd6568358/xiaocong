package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ValueInstantiator {
    public abstract String getValueTypeDesc();

    public boolean canInstantiate() {
        return canCreateUsingDefault() || canCreateUsingDelegate() || canCreateFromObjectWith() || canCreateFromString() || canCreateFromInt() || canCreateFromLong() || canCreateFromDouble() || canCreateFromBoolean();
    }

    public boolean canCreateFromString() {
        return false;
    }

    public boolean canCreateFromInt() {
        return false;
    }

    public boolean canCreateFromLong() {
        return false;
    }

    public boolean canCreateFromDouble() {
        return false;
    }

    public boolean canCreateFromBoolean() {
        return false;
    }

    public boolean canCreateUsingDefault() {
        return getDefaultCreator() != null;
    }

    public boolean canCreateUsingDelegate() {
        return false;
    }

    public boolean canCreateFromObjectWith() {
        return false;
    }

    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
        return null;
    }

    public JavaType getDelegateType(DeserializationConfig config) {
        return null;
    }

    public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + "; no default creator found");
    }

    public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) throws IOException {
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " with arguments");
    }

    public Object createUsingDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " using delegate");
    }

    public Object createFromString(DeserializationContext ctxt, String value) throws IOException {
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON String");
    }

    public Object createFromInt(DeserializationContext ctxt, int value) throws IOException {
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON int number");
    }

    public Object createFromLong(DeserializationContext ctxt, long value) throws IOException {
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON long number");
    }

    public Object createFromDouble(DeserializationContext ctxt, double value) throws IOException {
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON floating-point number");
    }

    public Object createFromBoolean(DeserializationContext ctxt, boolean value) throws IOException {
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON boolean value");
    }

    public AnnotatedWithParams getDefaultCreator() {
        return null;
    }

    public AnnotatedWithParams getDelegateCreator() {
        return null;
    }

    public AnnotatedWithParams getWithArgsCreator() {
        return null;
    }
}
